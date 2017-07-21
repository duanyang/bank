package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer>{
	
	@Transactional
	@Query("select l from Location l where l.zip= :zip")
	List<Location> lookup(@Param("zip") String zip);
	
}
