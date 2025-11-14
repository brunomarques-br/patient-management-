package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.service.PatientService;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        return ResponseEntity.ok().body(patientService.getPatients());
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(
            //trigger to validate all fields in PatientRequestDTO and a custom validator created
            @Validated({Default.class, CreatePatientValidationGroup.class})
            @RequestBody // Convert JSON request -> DTO Request
            PatientRequestDTO patientRequestDTO
    ) {
        return ResponseEntity.ok().body(patientService.createPatient(patientRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable
            UUID id,
            @Validated({Default.class}) //trigger to validate all fields in PatientRequestDTO
            @RequestBody
            PatientRequestDTO patientRequestDTO
    ) {
        return ResponseEntity.ok().body(patientService.updatePatient(id, patientRequestDTO));
    }

}
