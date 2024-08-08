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
@Table(name="notice") 
public class Notice  {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;   
	private String title; 
	private String notice;
	private String audience; 
	private Date fromdate;
	private Date todate; 
	private Timestamp date_added;
	private Timestamp date_updated; 
}