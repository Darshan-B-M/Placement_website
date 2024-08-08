package com.project.pts.repository;

 
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
 
import com.project.pts.entity.Branch;

public interface  BranchRepository  extends JpaRepository<Branch, Integer> {
	Branch	findById(int id);  
	Branch findByName(String un);
	Branch findByCourseAndName(int cint, String name);
	List<Branch> findByCourse(int id);  
}
