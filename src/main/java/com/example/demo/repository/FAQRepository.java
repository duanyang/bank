package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.FAQ;

public interface FAQRepository extends JpaRepository<FAQ, Integer>{
	
	
	@Transactional
	@Query("select f from FAQ f where f.question like concat ('%',:keyword,'%')")
	
	List<FAQ> find(@Param("keyword") String parameter);

}
