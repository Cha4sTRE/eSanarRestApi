package cj.esanar.service.implement;

import cj.esanar.persistence.entity.PatientEntity;
import cj.esanar.persistence.repository.PatientRepository;
import cj.esanar.service.PatientService;
import cj.esanar.service.dtos.PatientDto;
import cj.esanar.util.PatientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;


    @Override
    public Page<PatientDto> listPatients(Pageable pageable) {
        Page<PatientDto> dtoPage= patientRepository.findAll(pageable).map(patientMapper::toPatientDto);
        return dtoPage;
    }

    @Override
    public PatientDto findPatientsById(Long id) {
        PatientEntity patient= patientRepository.findById(id).orElse(null);
        return patientMapper.toPatientDto(patient);
    }

    @Override
    public PatientDto savePatients(PatientEntity paciente) {
        patientRepository.save(paciente);
        return patientMapper.toPatientDto(paciente);
    }


}
