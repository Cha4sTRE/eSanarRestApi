package cj.esanar.service.implement;

import cj.esanar.persistence.entity.PatientEntity;
import cj.esanar.persistence.repository.PatientRepository;
import cj.esanar.service.PatientService;
import cj.esanar.service.dtos.PatientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;


    @Override
    public ResponseEntity<List<PatientDto>> listPatients() {
        return null;
    }

    @Override
    public ResponseEntity<Page<PatientDto>> listPatients(Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<PatientDto> findPatientsById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<PatientDto> savePatients(PatientEntity paciente) {
        return null;
    }

    @Override
    public ResponseEntity<PatientDto> deletePatients(PatientEntity paciente) {
        return null;
    }
}
