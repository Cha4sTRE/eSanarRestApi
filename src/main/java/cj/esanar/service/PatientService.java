package cj.esanar.service;

import cj.esanar.persistence.entity.PatientEntity;
import cj.esanar.service.dtos.in.PatientRequest;
import cj.esanar.service.dtos.out.PatientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {

    Page<PatientDto> listPatients(Pageable pageable);
    PatientDto findPatientsById(Long id);
    PatientDto savePatients(PatientRequest patient);

}
