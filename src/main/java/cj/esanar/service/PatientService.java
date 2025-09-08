package cj.esanar.service;

import cj.esanar.persistence.entity.PatientEntity;
import cj.esanar.service.dtos.PatientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PatientService {

    ResponseEntity<List<PatientDto>> listPatients();

    ResponseEntity<Page<PatientDto>> listPatients(Pageable pageable);
    ResponseEntity<PatientDto> findPatientsById(Long id);
    ResponseEntity<PatientDto> savePatients(PatientEntity paciente);
    ResponseEntity<PatientDto> deletePatients(PatientEntity paciente);

}
