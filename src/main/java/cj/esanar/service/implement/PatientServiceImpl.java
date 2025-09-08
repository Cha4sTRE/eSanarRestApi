package cj.esanar.service.implement;

import cj.esanar.persistence.entity.PatientEntity;
import cj.esanar.persistence.repository.PatientRepository;
import cj.esanar.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public Page<PatientEntity> listPatients(Pageable page, String filtro) {
        return listPatients(page);
    }

    @Override
    public Page<PatientEntity> listPatients(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    @Override
    public List<PatientEntity> listPatients() {
        return patientRepository.findAll();
    }

    @Override
    public PatientEntity findPatientsById(PatientEntity paciente) {
        return patientRepository.findById(paciente.getId()).orElse(null);
    }

    @Override
    public void savePatients(PatientEntity paciente) {
        paciente.setAge(calculateEdad(paciente.getBirthDate()));
        patientRepository.save(paciente);
    }
    @Override
    public void deletePatients(PatientEntity paciente) {
        patientRepository.delete(paciente);
    }

    private int calculateEdad(LocalDate fechaNacimiento){

        LocalDate today = LocalDate.now();
        Period period= Period.between(fechaNacimiento,today);
        int years = period.getYears();
        return years;
    }
}
