package com.project.pts.entity;
 
import java.sql.Date;
import java.sql.Timestamp;

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
@Table(name="trainings_academicyear") 
public class Trainingacademicyear  {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;   
	private int academicyear; 
	private int training; 
}