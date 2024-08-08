package com.project.pts.repository;

 
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.project.pts.entity.Student;

public interface  StudentRepository  extends JpaRepository<Student, Integer> {
	Student	findById(int id);

	Student findByUser(int id);

	Student findByRegno(String regno);   
}
