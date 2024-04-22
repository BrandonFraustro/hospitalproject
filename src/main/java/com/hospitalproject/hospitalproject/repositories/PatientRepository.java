package com.hospitalproject.hospitalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospitalproject.hospitalproject.entities.Patients;

@Repository
public interface PatientRepository extends JpaRepository<Patients, Long>{
    
}
