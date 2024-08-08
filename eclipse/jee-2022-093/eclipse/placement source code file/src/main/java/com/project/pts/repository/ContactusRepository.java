package com.project.pts.repository;
 
import com.project.pts.entity.Contactus;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository; 

public interface ContactusRepository extends JpaRepository<Contactus, Long> {
	Contactus findById(long id); 
	List<Contactus> findAllByOrderByIdDesc(Pageable page);
}


 