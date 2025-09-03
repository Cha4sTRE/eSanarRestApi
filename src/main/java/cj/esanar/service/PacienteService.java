package cj.esanar.service;

import cj.esanar.persistence.entity.PacienteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PacienteService {

    List<PacienteEntity> listPatients();

    Page<PacienteEntity> listPatients(Pageable pageable,String filtro);
    Page<PacienteEntity> listPatients(Pageable pageable);
    PacienteEntity findPatientsById(PacienteEntity paciente);
    void savePatients(PacienteEntity paciente);
    void deletePatients(PacienteEntity paciente);

}
