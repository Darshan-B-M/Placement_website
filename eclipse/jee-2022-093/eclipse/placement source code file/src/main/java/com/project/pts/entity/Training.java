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
@Table(name="trainings") 
public class Training  {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;   
	private String title; 
	private String description; 
	private String location; 
	private Date fromdate;
	private Date todate; 
	private int status;
	private Timestamp date_added;
	private Timestamp date_updated; 
}