package com.hospitalproject.hospitalproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Patients")
public class Patients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long idPatient;

    @Getter @Setter
    private String namePatient;

    @Getter @Setter
    private int age;

    @Getter @Setter
    private String phone;
    
    @Getter @Setter
    private String address;
}
