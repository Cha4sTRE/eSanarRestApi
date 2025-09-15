package cj.esanar.controller;

import cj.esanar.service.PatientService;
import cj.esanar.service.dtos.in.PatientRequest;
import cj.esanar.service.dtos.out.PatientDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/esanar/api/v1/patients")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/list")
    public ResponseEntity<Page<PatientDto>> newPatient(@RequestParam(name = "page", defaultValue = "0") int pageNumber,
                                                       @RequestParam(name = "size",defaultValue = "3") int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PatientDto> patientDtoPage= patientService.listPatients(pageable);

        return new ResponseEntity<>(patientDtoPage, HttpStatus.OK);

    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<PatientDto> findPatientById(@PathVariable Long id) {
        PatientDto patientDto= patientService.findPatientsById(id);
        return new ResponseEntity<>(patientDto, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<PatientDto> newPatient(@RequestBody @Valid PatientRequest patient) {
        PatientDto patientDto=patientService.savePatients(patient);
        return new ResponseEntity<>(patientDto, HttpStatus.CREATED);
    }

}
