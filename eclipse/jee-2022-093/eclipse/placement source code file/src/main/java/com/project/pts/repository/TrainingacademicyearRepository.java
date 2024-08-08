package com.project.pts.repository;

 
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.project.pts.entity.Trainingacademicyear;

public interface  TrainingacademicyearRepository  extends JpaRepository<Trainingacademicyear, Integer> {
	Trainingacademicyear	findById(int id);

	@Transactional
	void deleteByTraining(int id);

	List<Trainingacademicyear> findByTraining(int id);

	List<Trainingacademicyear> findByAcademicyear(int ayear);   
}
