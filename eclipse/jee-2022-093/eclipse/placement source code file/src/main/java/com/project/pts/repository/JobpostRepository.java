package com.project.pts.repository;

 
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
 
import com.project.pts.entity.Jobpost;

public interface  JobpostRepository  extends JpaRepository<Jobpost, Integer> {
	Jobpost	findById(int id);

	List<Jobpost> findAllByOrderByIdDesc();

	List<Jobpost> findByCompanyOrderByIdDesc(int id);

	List<Jobpost> findByStatusAndIdIn(int i, List<Integer> intersect);   
}
