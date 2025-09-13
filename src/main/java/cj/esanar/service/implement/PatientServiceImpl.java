package cj.esanar.service.implement;

import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.persistence.entity.PatientEntity;
import cj.esanar.persistence.repository.PatientRepository;
import cj.esanar.service.PatientService;
import cj.esanar.service.dtos.in.PatientRequest;
import cj.esanar.service.dtos.out.PatientDto;
import cj.esanar.util.PatientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;

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
    public PatientDto savePatients(PatientRequest patient) {
        PatientEntity patientEntity= patientMapper.toPatient(patient);
        HistoryEntity history= new HistoryEntity(null,LocalDate.now(),patientEntity, Collections.emptySet());
        patientEntity.setHistoryEntity(history);
        patientRepository.save(patientEntity);
        return patientMapper.toPatientDto(patientEntity);
    }


}
