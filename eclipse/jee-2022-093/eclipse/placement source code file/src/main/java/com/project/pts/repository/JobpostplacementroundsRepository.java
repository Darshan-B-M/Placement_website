package com.project.pts.repository;

 
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.project.pts.entity.Jobpostplacementrounds;

public interface  JobpostplacementroundsRepository  extends JpaRepository<Jobpostplacementrounds, Integer> {
	Jobpostplacementrounds	findById(int id);

	@Transactional
	void deleteByJobpost(int id);

	List<Jobpostplacementrounds> findByJobpost(int id);   
}
