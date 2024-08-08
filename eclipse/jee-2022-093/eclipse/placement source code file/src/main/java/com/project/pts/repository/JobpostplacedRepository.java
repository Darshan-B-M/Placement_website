package com.project.pts.repository;

 
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.project.pts.entity.Jobpostplaced;

public interface  JobpostplacedRepository  extends JpaRepository<Jobpostplaced, Integer> {
	Jobpostplaced	findById(int id);
 

	List<Jobpostplaced> findByJobpostapply(int id);


	List<Jobpostplaced> findByJobpost(int id);


	Jobpostplaced findByJobpostapplyAndPlacementround(int id, int placementround);

    @Transactional
	void deleteByJobpost(int id);   
}
