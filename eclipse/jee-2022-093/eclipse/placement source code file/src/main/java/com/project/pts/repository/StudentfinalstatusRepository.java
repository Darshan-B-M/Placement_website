package com.project.pts.repository;

 
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
  
import com.project.pts.entity.Studentfinalstatus;

public interface  StudentfinalstatusRepository  extends JpaRepository<Studentfinalstatus, Integer> {
	Studentfinalstatus	findById(int id);

	Studentfinalstatus findByStudent(int id); 
 
}
