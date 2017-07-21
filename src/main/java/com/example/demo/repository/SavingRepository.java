package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.SavingAccount;
import com.example.demo.model.User;

@Repository
public interface SavingRepository extends JpaRepository<SavingAccount, Integer>{
	
	@Transactional
	@Query ("select c from SavingAccount c where c.accountNumber= :account and c.routing= :routing")
	SavingAccount find(@Param("account") String account, @Param("routing") String routing);
	
	@Transactional
	@Modifying
	@Query("delete from SavingAccount s where s.user= :user")
	void closeAccount(@Param("user") User user);

}
