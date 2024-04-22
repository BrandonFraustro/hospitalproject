package com.hospitalproject.hospitalproject.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalproject.hospitalproject.entities.Patients;
import com.hospitalproject.hospitalproject.repositories.PatientRepository;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<Patients> getAllPatients(){
        return patientRepository.findAll();
    }

    public Optional<Patients> getPatient(Long idPatient){
        try {
            return patientRepository.findById(idPatient);
        } catch(Exception e) {
            logger.error("PatientService:getPatient ", e);
            throw e;
        }
    }

    public Patients addPatient(Patients patientContent){
        try {
            return patientRepository.save(patientContent);
        } catch(Exception e) {
            logger.error("PatientService:addPatient ", e);
            throw e;
        }
    }

    public boolean deletePatient(Long idPatient) {
        boolean canBeDeleted = false;

        try {
            patientRepository.deleteById(idPatient);
            canBeDeleted = true;
            return canBeDeleted;
        } catch (Exception e) {
            logger.error("PatientService:deletePatient ", e);
            throw e;
        }
    }

    public Patients updatePatient(Long idPatient, Patients patientContent) {
        Patients patientFounded = patientRepository.findById(idPatient).get();

        patientFounded.setNamePatient(patientContent.getNamePatient());
        patientFounded.setAge(patientContent.getAge());
        patientFounded.setPhone(patientContent.getPhone());
        patientFounded.setAddress(patientContent.getAddress());

        try {
            return patientRepository.save(patientFounded);
        } catch (Exception e) {
            logger.error("PatientService:updatePatient ", e);
            throw e;
        }
    }
}
