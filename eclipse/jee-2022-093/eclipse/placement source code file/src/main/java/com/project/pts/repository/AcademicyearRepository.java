package com.project.pts.repository;

 
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.project.pts.entity.Academicyear;

public interface  AcademicyearRepository  extends JpaRepository<Academicyear, Integer> {
	Academicyear	findById(int id);  
	Academicyear findByName(String un);  
}
