package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Admin;


public interface AdminRepository extends JpaRepository<Admin, Integer>{

	@Transactional
	@Query("select a from Admin a where a.username= :uname and a.password= :psw")
	Admin login(@Param("uname") String uname, @Param("psw")String md5Hex);

}
