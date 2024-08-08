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
@Table(name="contactus")
public class Contactus
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String email;
 
    
    @Column(nullable=false)
    private String message;
    
    private Timestamp creationtime; 
    
    private String ipaddress;
    
}
