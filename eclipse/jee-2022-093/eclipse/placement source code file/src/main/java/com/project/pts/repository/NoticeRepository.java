package com.project.pts.repository;

 
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.project.pts.entity.Notice;

public interface  NoticeRepository  extends JpaRepository<Notice, Integer> {
	Notice	findById(int id);   
}
