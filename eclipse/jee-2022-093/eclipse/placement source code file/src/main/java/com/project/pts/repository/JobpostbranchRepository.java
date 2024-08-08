package com.project.pts.repository;

 
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
 
import com.project.pts.entity.Jobpostbranch;

public interface  JobpostbranchRepository  extends JpaRepository<Jobpostbranch, Integer> {
	Jobpostbranch	findById(int id);

	List<Jobpostbranch> findByJobpost(int id);   
	@Transactional
	void deleteByJobpost(int id);

	List<Jobpostbranch> findByBranch(int branch);

	Jobpostbranch findByJobpostAndBranch(int id, int branch);
 
}
