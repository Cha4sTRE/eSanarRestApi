package cj.esanar.service;

import cj.esanar.persistence.entity.PatientEntity;
import cj.esanar.service.dtos.PatientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PatientService {

    Page<PatientDto> listPatients(Pageable pageable);
    PatientDto findPatientsById(Long id);
    PatientDto savePatients(PatientEntity paciente);

}
