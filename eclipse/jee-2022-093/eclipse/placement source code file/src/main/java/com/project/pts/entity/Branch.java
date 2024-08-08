package com.project.pts.entity;
 
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="branch") 
public class Branch  {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;  
	private int course;
	private String name; 
}