package com.project.pts.repository;

 
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.project.pts.entity.Jobpostskill;

public interface  JobpostskillRepository  extends JpaRepository<Jobpostskill, Integer> {
	Jobpostskill	findById(int id);

	@Transactional
	void deleteByJobpost(int id);

	List<Jobpostskill> findByJobpost(int id);
 
}
