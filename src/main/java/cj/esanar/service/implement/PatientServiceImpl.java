package cj.esanar.service.implement;

import cj.esanar.persistence.entity.PacienteEntity;
import cj.esanar.persistence.repository.PacienteRepository;
import cj.esanar.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class PatientServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public Page<PacienteEntity> listPatients(Pageable page,String filtro) {
        if(filtro!=null||filtro==""){
            return pacienteRepository.findByfilter(page, filtro);
        }
        return listPatients(page);
    }

    @Override
    public Page<PacienteEntity> listPatients(Pageable pageable) {
        return pacienteRepository.findAll(pageable);
    }

    @Override
    public List<PacienteEntity> listPatients() {
        return pacienteRepository.findAll();
    }

    @Override
    public PacienteEntity findPatientsById(PacienteEntity paciente) {
        return pacienteRepository.findById(paciente.getId()).orElse(null);
    }

    @Override
    public void savePatients(PacienteEntity paciente) {
        paciente.setEdad(calculateEdad(paciente.getFechaNacimiento()));
        pacienteRepository.save(paciente);
    }
    @Override
    public void deletePatients(PacienteEntity paciente) {
        pacienteRepository.delete(paciente);
    }

    private int calculateEdad(LocalDate fechaNacimiento){

        LocalDate today = LocalDate.now();
        Period period= Period.between(fechaNacimiento,today);
        int years = period.getYears();
        return years;
    }
}
