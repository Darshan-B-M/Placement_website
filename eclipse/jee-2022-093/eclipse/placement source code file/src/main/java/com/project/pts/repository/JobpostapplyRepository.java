package com.project.pts.repository;

 
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
 
import com.project.pts.entity.Jobpostapply;
import com.project.pts.entity.Jobpostplaced;

public interface  JobpostapplyRepository  extends JpaRepository<Jobpostapply, Integer> {
	Jobpostapply	findById(int id);

	Jobpostapply findByStudentAndJobpost(int id, int id2);

	List<Jobpostapply> findByJobpost(int id);
 
}
