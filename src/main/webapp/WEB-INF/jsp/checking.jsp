<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="../css/style.css"/>
<title>Insert title here</title>
</head>
<body>
<img  class="bank" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQEoSsSEqrWznDYj8o2FRqwSkWFDQDQ9MtamSiKC0OLPhtlSrFGYQ">
<c:choose>
<c:when test="${not empty userid }">
<table class="checkingDetail">
<tr>
<td>Account Number</td>
<td>Routing Number</td>
<td>Checking Amount</td>
</tr>
<tr>
<td>${checking.accountNumber }</td>
<td>${checking.routing}</td>
<td>$${checking.amount }</td>
</tr>
</table>
<c:if test="${not empty transactions }">
<table class="checkingTransaction">
<tr>
<td>Transaction Type</td>
<td>Transaction Description</td>
<td>Transaction Amount</td>
<td>Transaction Date</td>
</tr>
<c:forEach items="${transactions }" var="transaction">
<tr class="saving">
<td class="saving">${transaction.type.name}</td>
<td class="saving">${transaction.type.description}</td>
<td class="saving">${transaction.amount}</td>
<td class="saving">${transaction.date}</td>
</tr>
</c:forEach>
</table>
<table class="pageCount">
<c:forEach var ="i" begin="1" end="${pagesCount}">
<td>
<a href="${pageContext.request.contextPath}/onlinebank/saving?pk=${checking.id}&pageSize=5&pageNum=${i}">${i}</a>
</td>
</c:forEach>
</table>
</c:if>
<a  class="back" href="${pageContext.request.contextPath}/onlinebank/viewAccounts">Back</a></br>
<a class="pdf" href="${pageContext.request.contextPath}/onlinebank/checkingpdfgenerate?pk=${checking.id}">Generate PDF Of All Transactions</a>
</c:when>
<c:otherwise>
<jsp:forward page="home.jsp"/>
</c:otherwise>
</c:choose>
</body>
</html>