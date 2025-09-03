package cj.esanar.service;

import cj.esanar.persistence.entity.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {

    List<PatientEntity> listPatients();

    Page<PatientEntity> listPatients(Pageable pageable, String filtro);
    Page<PatientEntity> listPatients(Pageable pageable);
    PatientEntity findPatientsById(PatientEntity paciente);
    void savePatients(PatientEntity paciente);
    void deletePatients(PatientEntity paciente);

}
