package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;



@Repository
public interface UserRespository extends JpaRepository<User, Integer>{
	
	@Transactional
	@Query("select u from User u where u.username= :uname and u.password= :psw")
	User login(@Param("uname") String uname, @Param("psw")String md5Hex);
	
}
