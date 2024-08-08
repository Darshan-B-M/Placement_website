package com.project.pts.entity;
 
import java.sql.Date;

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
@Table(name="student") 
public class Student  {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;  
	private int user;
	private String regno; 
	private String address; 
	private int joiningyear;
	private Date dob; 
	private String aboutme; 
	private int course;
	private int branch;
	private int ayear;
	private float avgscore;
}