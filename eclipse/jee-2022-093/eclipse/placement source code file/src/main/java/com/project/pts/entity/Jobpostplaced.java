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
@Table(name="jobpost_placed") 
public class Jobpostplaced  {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;  
	private int  jobpost; 
	private int jobpostapply; 
	private int placementround;
	private int status;
	private Timestamp date_updated; 
}