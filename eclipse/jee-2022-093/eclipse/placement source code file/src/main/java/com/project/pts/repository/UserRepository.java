package com.project.pts.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.pts.LoginTypes; 
import com.project.pts.entity.User;

public interface  UserRepository  extends JpaRepository<User, Integer> {
	User	findById(int id); 
    List<User>  findAllByOrderByIdDesc(Pageable p);  
	List<User>   findByTypeOrderByIdDesc(String type);   
	User findByEmail(String email);
	User findByContact(String contact); 
	List<User> findAllByOrderByIdDesc();
	List<User> findByStatusAndTypeAndNameContaining(int i, String type, String key); 	
}
