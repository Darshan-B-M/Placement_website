package com.project.pts.entity;
 
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
@Table(name="studentfinalstatus") 
public class Studentfinalstatus  {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;   
	private int student;
	private String status;
	private Timestamp updationtime;
}