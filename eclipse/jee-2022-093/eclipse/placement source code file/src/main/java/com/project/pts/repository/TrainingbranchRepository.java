package com.project.pts.repository;

 
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
 
import com.project.pts.entity.Trainingbranch;

public interface  TrainingbranchRepository  extends JpaRepository<Trainingbranch, Integer> {
	Trainingbranch	findById(int id);
	@Transactional
	void deleteByTraining(int id);
	List<Trainingbranch> findByTraining(int id);
	List<Trainingbranch> findByBranch(int branch);   
}
