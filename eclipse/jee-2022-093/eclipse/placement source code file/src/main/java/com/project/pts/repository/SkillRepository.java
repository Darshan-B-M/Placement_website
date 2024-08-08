package com.project.pts.repository;

 
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.project.pts.entity.Skill;

public interface  SkillRepository  extends JpaRepository<Skill, Integer> {
	Skill	findById(int id);  
	Skill findByName(String un);  
}
