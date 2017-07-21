package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.CheckingAccount;
import com.example.demo.model.SavingAccount;
import com.example.demo.model.Transaction;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer>{
	
	@Transactional
	@Query("select t from Transaction t where t.account= :saving")
	List<Transaction> findSaving (@Param("saving") SavingAccount savingAccount, Pageable page);
	
	@Transactional
	@Query("select t from Transaction t where t.account= :checking")
	List<Transaction> findChecking (@Param("checking") CheckingAccount checkingAccount, Pageable page);

}
