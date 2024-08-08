package com.project.pts.repository;

 
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.project.pts.entity.Company;

public interface  CompanyRepository  extends JpaRepository<Company, Integer> {
	Company	findById(int id);

	Company findByUser(int id);   
}
