package com.project.pts.repository;

 
  
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
 
import com.project.pts.entity.Training; 
 

public interface  TrainingRepository  extends JpaRepository<Training, Integer> {
	Training	findById(int id);
 

	List<Training> findAllByOrderByIdDesc();


	List<Training> findByStatusAndIdIn(int status,List<Integer> intersect);


	 
}
