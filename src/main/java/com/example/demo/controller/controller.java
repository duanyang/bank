package com.example.demo.controller;

import java.awt.Window;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.TransactionalException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Account;
import com.example.demo.model.Admin;
import com.example.demo.model.CheckingAccount;
import com.example.demo.model.FAQ;
import com.example.demo.model.Location;
import com.example.demo.model.SavingAccount;
import com.example.demo.model.Transaction;
import com.example.demo.model.TransactionType;
import com.example.demo.model.User;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.CheckingRepository;
import com.example.demo.repository.FAQRepository;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.SavingRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.TransactionTypeRepository;
import com.example.demo.repository.UserRespository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping("/onlinebank")
public class controller {

	@Autowired
	UserRespository userRepository;

	@Autowired
	SavingRepository savingRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	CheckingRepository checkingRepository;

	@Autowired
	TransactionTypeRepository transactionTypeRespository;

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	FAQRepository faqRepository;
	
	@Autowired
	AdminRepository adminRepository;

	@GetMapping("home")
	public ModelAndView home() {
		return new ModelAndView("home");
	}

	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@PostMapping("/processLogin")
	public ModelAndView processLogin(HttpServletRequest request) {
		final String admin = "admin";
		final String customer = "customer";
		String uname = request.getParameter("uname");
		String psw = request.getParameter("psw");
		String role = request.getParameter("role");
		if (role.equals(customer)) {
			User user = userRepository.login(uname, DigestUtils.md5Hex(psw));
		
			if (user != null) {
				request.getSession().setAttribute("userid", user.getId());
				return new ModelAndView("userMenu");
			}
			else{
				return new ModelAndView("home");
			}
		}
		else{
			Admin administrator=adminRepository.login(uname, DigestUtils.md5Hex(psw));
			if (administrator!=null){
				request.getSession().setAttribute("userid", administrator.getId());
				return new ModelAndView("admin");
			}
			else{
				return new ModelAndView("home");
			}
		}
	}

	@GetMapping("/viewAccounts")
	public ModelAndView getAccounts(HttpServletRequest request) {
		int id = (int) request.getSession().getAttribute("userid");
		User u = userRepository.getOne(id);
		ModelAndView model = new ModelAndView("userMenu");
		if (u.getCheckingAccount() != null)
			model.addObject("checking", u.getCheckingAccount());
		if (u.getSavingAccount() != null)
			model.addObject("saving", u.getSavingAccount());
		return model;
	}

	@GetMapping("/transfer")
	public ModelAndView transfer(HttpServletRequest request) {
		return new ModelAndView("userMenu", "transfer", "yes");

	}

	@GetMapping("/saving")
	public ModelAndView getSaving(@RequestParam("pk") String pk, @RequestParam("pageSize") String pageSize,
			@RequestParam("pageNum") String pageNum) {
		SavingAccount savingAccount = savingRepository.findOne(Integer.parseInt(pk));
		int rowsize = savingAccount.getTransactions().size();
		int pagesize = Integer.parseInt(pageSize);
		int pagecount = Integer.parseInt(pageNum) - 1;
		int pagesCount = (rowsize - 1) / pagesize + 1;
		Pageable page = new PageRequest(pagecount, pagesize, Direction.DESC, "date");
		List<Transaction> transactions = transactionRepository.findSaving(savingAccount, page);
		ModelAndView model = new ModelAndView("saving");
		model.addObject("saving", savingAccount);
		model.addObject("pagesCount", pagesCount);
		model.addObject("transactions", transactions);
		return model;
	}

	@GetMapping("/savingpdfgenerate")
	public void generateSavingPDF(@RequestParam("pk") String pk, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SavingAccount savingAccount = savingRepository.findOne(Integer.parseInt(pk));
		Set<Transaction> transactions = savingAccount.getTransactions();
		// 告诉浏览器用什么软件可以打开此文件
		response.setHeader("content-Type", "application/pdf");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition", "attachment;filename=savingtransactions.pdf");
		Document document = new Document();
		PdfWriter.getInstance(document, response.getOutputStream());
		Image img = Image.getInstance(
				"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQEoSsSEqrWznDYj8o2FRqwSkWFDQDQ9MtamSiKC0OLPhtlSrFGYQ");
		float scaler = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin())
				/ img.getWidth()) * 100;
		img.scalePercent(scaler);
		document.open();
		document.add(img);
		PdfPTable table = new PdfPTable(4);
		table.addCell("Type");
		table.addCell("Description");
		table.addCell("Amount");
		table.addCell("Date");
		for (Transaction transaction : transactions) {
			table.addCell(transaction.getType().getName());
			table.addCell(transaction.getType().getDescription());
			table.addCell(transaction.getAmount());
			table.addCell(transaction.getDate());
		}
		document.add(table);
		document.close();

	}

	@GetMapping("/checkingpdfgenerate")
	public void generateCheckingPdf(@RequestParam("pk") String pk, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CheckingAccount checkingAccount = checkingRepository.findOne(Integer.parseInt(pk));
		Set<Transaction> transactions = checkingAccount.getTransactions();
		// 告诉浏览器用什么软件可以打开此文件
		response.setHeader("content-Type", "application/pdf");
		// 下载文件的默认名称
		response.setHeader("Content-Disposition", "attachment;filename=savingtransactions.pdf");
		Document document = new Document();
		PdfWriter.getInstance(document, response.getOutputStream());
		Image img = Image.getInstance(
				"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQEoSsSEqrWznDYj8o2FRqwSkWFDQDQ9MtamSiKC0OLPhtlSrFGYQ");
		float scaler = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin())
				/ img.getWidth()) * 100;
		img.scalePercent(scaler);
		document.open();
		document.add(img);
		PdfPTable table = new PdfPTable(4);
		table.addCell("Type");
		table.addCell("Description");
		table.addCell("Amount");
		table.addCell("Date");
		for (Transaction transaction : transactions) {
			table.addCell(transaction.getType().getName());
			table.addCell(transaction.getType().getDescription());
			table.addCell(transaction.getAmount());
			table.addCell(transaction.getDate());
		}
		document.add(table);
		document.close();
	}

	@GetMapping("/checking")
	public ModelAndView getChecking(@RequestParam("pk") String pk, @RequestParam("pageSize") String pageSize,
			@RequestParam("pageNum") String pageNum) {
		CheckingAccount checkingAccount = checkingRepository.findOne(Integer.parseInt(pk));
		int rowsize = checkingAccount.getTransactions().size();
		int pagesize = Integer.parseInt(pageSize);
		int pagecount = Integer.parseInt(pageNum) - 1;
		int pagesCount = (rowsize - 1) / pagesize + 1;
		Pageable page = new PageRequest(pagecount, pagesize, Direction.DESC, "date");
		List<Transaction> transactions = transactionRepository.findChecking(checkingAccount, page);
		ModelAndView model = new ModelAndView("checking");
		model.addObject("checking", checkingAccount);
		model.addObject("pagesCount", pagesCount);
		model.addObject("transactions", transactions);
		return model;
	}

	@PostMapping("/processTransfer")
	public ModelAndView processTransfer(HttpServletRequest request) {
		final String check = "Checking";
		final String save = "Saving";
		BigInteger original, change, result;
		String fromAccount = request.getParameter("fromAccount");
		String fromRouting = request.getParameter("fromRouting");
		String toAccount = request.getParameter("toAccount");
		String toRouting = request.getParameter("toRouting");
		String fromSelect = request.getParameter("fromSelect");
		String toSelect = request.getParameter("toSelect");
		String amount = request.getParameter("amount");
		TransactionType typeFrom = new TransactionType("withdraw", "Wired Transfer");
		TransactionType typeTo = new TransactionType("deposit", "Wired Transfer");
		transactionTypeRespository.save(typeFrom);
		transactionTypeRespository.save(typeTo);
		DateFormat df = new SimpleDateFormat("mm/dd/yyyy HH:mm:ss");
		Calendar calobj = Calendar.getInstance();
		Transaction transactionFrom = new Transaction(amount, calobj.getTime().toString());
		Transaction transactionTo = new Transaction(amount, calobj.getTime().toString());
		transactionFrom.setType(typeFrom);
		transactionTo.setType(typeTo);
		if (fromSelect.equals(check)) {
			CheckingAccount checkingAccount = checkingRepository.find(fromAccount, fromRouting);
			original = new BigInteger(checkingAccount.getAmount());
			change = new BigInteger(amount);
			result = original.subtract(change);
			checkingAccount.setAmount(result.toString());
			transactionFrom.setAccount(checkingAccount);
			transactionRepository.save(transactionFrom);
			checkingRepository.save(checkingAccount);
		}
		if (fromSelect.equals(save)) {
			SavingAccount savingAccount = savingRepository.find(fromAccount, fromRouting);
			original = new BigInteger(savingAccount.getAmount());
			change = new BigInteger(amount);
			result = original.subtract(change);
			savingAccount.setAmount(result.toString());
			transactionFrom.setAccount(savingAccount);
			transactionRepository.save(transactionFrom);
			savingRepository.save(savingAccount);
		}
		if (toSelect.equals(check)) {
			CheckingAccount checkingAccount = checkingRepository.find(toAccount, toRouting);
			original = new BigInteger(checkingAccount.getAmount());
			change = new BigInteger(amount);
			result = original.add(change);
			checkingAccount.setAmount(result.toString());
			transactionTo.setAccount(checkingAccount);
			transactionRepository.save(transactionTo);
			checkingRepository.save(checkingAccount);
		}
		if (toSelect.equals(save)) {
			SavingAccount savingAccount = savingRepository.find(toAccount, toRouting);
			original = new BigInteger(savingAccount.getAmount());
			change = new BigInteger(amount);
			result = original.add(change);
			savingAccount.setAmount(result.toString());
			transactionTo.setAccount(savingAccount);
			transactionRepository.save(transactionTo);
			savingRepository.save(savingAccount);
		}
		return new ModelAndView("userMenu");
	}

	@GetMapping("/contact")
	public ModelAndView contact() {
		return new ModelAndView("contact");
	}

	@GetMapping("/locations")
	public ModelAndView location() {
		return new ModelAndView("location");
	}

	@PostMapping("/lookupLocation")
	public ModelAndView lookupLocation(HttpServletRequest request) {
		String zip = request.getParameter("zip");
		List<Location> locations = locationRepository.lookup(zip);
		return new ModelAndView("listlocation", "locations", locations);
	}

	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("userid");
		session.invalidate();
		return new ModelAndView("home");
	}

	@GetMapping("/faq")
	public ModelAndView faq() {
		return new ModelAndView("faq");
	}

	@PostMapping("/searchFAQ")
	public ModelAndView searchFAQ(HttpServletRequest request) {
		List<FAQ> faqs = faqRepository.find(request.getParameter("keyword"));
		return new ModelAndView("faqlist", "faqs", faqs);
	}

	@GetMapping("/getanswer")
	public ModelAndView getAnswer(@RequestParam("id") String id) {
		FAQ faq = faqRepository.findOne(Integer.parseInt(id));
		return new ModelAndView("faqanswer", "faq", faq);
	}
	
	@GetMapping("/addUser")
	public ModelAndView addUser(){
		return new ModelAndView("addUser");
	}
	
	@PostMapping("/processAddUser")
	public ModelAndView processAddUser(HttpServletRequest request){
		String name=request.getParameter("name");
		String address=request.getParameter("address");
		String dob=request.getParameter("dob");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String phone=request.getParameter("phone");
		String ssn=request.getParameter("ssn");
		String username=request.getParameter("username");
		User user=new User(name, dob, ssn, address, phone, email, username, DigestUtils.md5Hex(password));
		userRepository.save(user);
		return new ModelAndView("admin");
	}
	
	@GetMapping("/account")
	public ModelAndView addAccount(@RequestParam("pageSize") String pageSize, @RequestParam("pageNum") String pageCount){
		int pagesize=Integer.parseInt(pageSize);
		int pagecount=Integer.parseInt(pageCount)-1;
		int totalRow=userRepository.findAll().size();
		int pagesCount=(totalRow-1)/pagesize+1;
		Page<User>users=userRepository.findAll(new PageRequest(pagecount, pagesize, Direction.ASC, "name"));
		ModelAndView model=new ModelAndView();
		model.addObject("users", users.getContent());
		model.addObject("pagesCount", pagesCount);
		model.setViewName("listUsers");
		return model;
	}
	
	@GetMapping("/addAccountMenu")
	public ModelAndView addUserMenu(@RequestParam("id") String id){
		return new ModelAndView("addAccountMenu", "id", id);
	}
	
	@PostMapping("/processAddAccount")
	public ModelAndView processAddAccount(HttpServletRequest request){
		final String checking="checking";
		final String saving="saving";
		User user=userRepository.findOne(Integer.parseInt(request.getParameter("id")));
		String type=request.getParameter("type");
		String accountNumber=request.getParameter("accountNumber");
		String routing=request.getParameter("routing");
		String amount=request.getParameter("amount");
		if (type.equals(saving)){
			SavingAccount account=new SavingAccount();
			account.setAccountNumber(accountNumber);
			account.setAmount(amount);
			account.setRouting(routing);
			account.setInterest(1);
			account.setUser(user);
			savingRepository.save(account);
		}
		else{
			CheckingAccount account=new CheckingAccount();
			account.setAccountNumber(accountNumber);
			account.setAmount(amount);
			account.setRouting(routing);
			account.setUser(user);
			checkingRepository.save(account);
		}
		return new ModelAndView("admin");
	}
	
	@GetMapping("/closeAccountMenu")
	public ModelAndView closeMenu(@RequestParam("id") String id){
		User user=userRepository.findOne(Integer.parseInt(id));
		return new ModelAndView("deleteAccount", "user", user);
	}
	
	@RequestMapping("/deleteAccount")
	public ModelAndView deleteAccount(HttpServletRequest request){
		String id=request.getParameter("pk");
		String type=request.getParameter("type");
		User user=userRepository.findOne(Integer.parseInt(id));
		System.out.println(type);
		if(type.equals("save")){
			SavingAccount account=user.getSavingAccount();
			Set<Transaction> transactions=account.getTransactions();
			savingRepository.closeAccount(user);
			for (Transaction transaction: transactions){
				transactionRepository.delete(transaction);
			}
			
		}
		else{
			CheckingAccount account=user.getCheckingAccount();
			checkingRepository.delete(account);
		}
		return new ModelAndView("admin");
	}
	
}
