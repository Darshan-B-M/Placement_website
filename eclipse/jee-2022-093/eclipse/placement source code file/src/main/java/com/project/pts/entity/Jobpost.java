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
@Table(name="jobpost") 
public class Jobpost  {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;  
	private int company;
	private String jobtitle; 
	private String description;
	private String location; 
	private float minimumsalary;
	private float maximumsalary;
	private Date fromdate;
	private Date todate; 
	private float requiredscore;
	private int creator;
	private int status;
	private Timestamp date_added;
	private Timestamp date_updated; 
}