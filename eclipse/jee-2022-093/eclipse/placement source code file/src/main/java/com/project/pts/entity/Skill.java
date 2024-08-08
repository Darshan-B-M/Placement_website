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
@Table(name="skill") 
public class Skill  {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;   
	private String name; 
}