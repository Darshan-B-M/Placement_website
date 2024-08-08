package com.project.pts.repository;

 
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.project.pts.entity.Placementrounds;

public interface  PlacementroundsRepository  extends JpaRepository<Placementrounds, Integer> {
	Placementrounds	findById(int id);

	Placementrounds findByName(String name);   
}
