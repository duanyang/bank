package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.CheckingAccount;
import com.example.demo.model.User;

public interface CheckingRepository extends JpaRepository<CheckingAccount, Integer>{
	
	@Transactional
	@Query ("select c from CheckingAccount c where c.accountNumber= :account and c.routing= :routing")
	CheckingAccount find(@Param("account") String account, @Param("routing") String routing);
	
	@Transactional
	@Modifying
	@Query("delete from CheckingAccount c where c.user= :user")
	void closeAccount(@Param("user")User user);

}
