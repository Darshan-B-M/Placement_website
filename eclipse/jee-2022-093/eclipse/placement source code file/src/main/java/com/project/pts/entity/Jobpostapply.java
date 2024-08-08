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
@Table(name="jobpost_apply") 
public class Jobpostapply  {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;  
	private int  jobpost; 
	private int student;
	private int status;
	private Timestamp date_added;
	private Timestamp date_updated; 
}