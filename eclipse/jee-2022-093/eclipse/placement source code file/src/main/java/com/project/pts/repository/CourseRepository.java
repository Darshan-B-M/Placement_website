package com.project.pts.repository;

 
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.project.pts.entity.Course;

public interface  CourseRepository  extends JpaRepository<Course, Integer> {
	Course	findById(int id);  
	Course findByName(String un);  
}
