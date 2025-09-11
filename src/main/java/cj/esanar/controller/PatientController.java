package cj.esanar.controller;

import cj.esanar.service.PatientService;
import cj.esanar.service.dtos.PatientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/list")
    public ResponseEntity<Page<PatientDto>> newPatient(@RequestParam(name = "page", defaultValue = "0") int pageNumber,
                                                       @RequestParam(name = "size",defaultValue = "3") int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PatientDto> patientDtoPage= patientService.listPatients(pageable);

        return new ResponseEntity<>(patientDtoPage, HttpStatus.OK);

    }

}
