package com.project.pts.repository;

 
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.project.pts.entity.Jobpostacademicyear;

public interface  JobpostacademicyearRepository  extends JpaRepository<Jobpostacademicyear, Integer> {
	Jobpostacademicyear	findById(int id);

	@Transactional
	void deleteByJobpost(int id);
 
	List<Jobpostacademicyear> findByJobpost(int id);

	List<Jobpostacademicyear> findByAcademicyear(int ayear);

	Jobpostacademicyear findByJobpostAndAcademicyear(int id, int branch); 
}
