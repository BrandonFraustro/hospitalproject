package com.hospitalproject.hospitalproject.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.hospitalproject.hospitalproject.entities.Patients;
import com.hospitalproject.hospitalproject.services.PatientService;
import com.hospitalproject.hospitalproject.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
public class PatientController {
    @Autowired
    PatientService patientService;

    @Autowired
    JWTUtil jwtUtil;

    private String getTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    @GetMapping("/patients")
    public ResponseEntity<List<Patients>> getPatients(HttpServletRequest request) {
        String token = getTokenFromHeader(request);

        if(token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<Patients> pList = patientService.getAllPatients();

        return ResponseEntity.ok(pList);
    }

    @GetMapping("/patients/{idPatient}")
    public ResponseEntity<Optional<Patients>> getPatientById(HttpServletRequest request, @PathVariable Long idPatient) {
        String token = getTokenFromHeader(request);

        if(token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<Patients> uOptional = patientService.getPatient(idPatient);

        return ResponseEntity.ok(uOptional);
    }

    @PostMapping("/patients")
    public ResponseEntity<Patients> addPatient(HttpServletRequest request, @RequestBody Patients patientContent) {
        String token = getTokenFromHeader(request);

        if(token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Patients patientAdded = patientService.addPatient(patientContent);

        return ResponseEntity.ok(patientAdded);
    }
    
    @DeleteMapping("/patients/{idPatient}")
    public ResponseEntity<String> deletePatient(HttpServletRequest request, @PathVariable Long idPatient) {
        String token = getTokenFromHeader(request);

        if(token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        boolean deleted = patientService.deletePatient(idPatient);

        if (deleted) {
            return ResponseEntity.ok("Patient deleted correctly");
        } else {
            return ResponseEntity.badRequest().body("Error deleteng patient");
        }
    }

    @PutMapping("/patients/{idPatient}")
    public ResponseEntity<Patients> updatePatient(HttpServletRequest request, @PathVariable Long idPatient, @RequestBody Patients patientContent) {
        String token = getTokenFromHeader(request);

        if(token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Patients patientUpdated = patientService.updatePatient(idPatient, patientContent);
        return ResponseEntity.ok(patientUpdated);
    }
}
