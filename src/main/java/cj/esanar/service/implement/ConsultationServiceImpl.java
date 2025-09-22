package cj.esanar.service.implement;

import cj.esanar.persistence.entity.ConsultationEntity;
import cj.esanar.persistence.entity.PatientEntity;
import cj.esanar.persistence.entity.auth.UserEntity;
import cj.esanar.persistence.repository.ConsultationRepository;
import cj.esanar.persistence.repository.PatientRepository;
import cj.esanar.service.ConsultationService;
import cj.esanar.service.dtos.in.ConsultationRequest;
import cj.esanar.service.dtos.out.ConsultationDto;
import cj.esanar.util.ConsultationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultaRepository;
    private final PatientRepository patientRepository;
    private final ConsultationMapper consultationMapper;

    @Override
    public List<ConsultationDto> listConsultations() {
        List<ConsultationEntity> consultation=  consultaRepository.findAll();
        List<ConsultationDto> consultationDtos= consultationMapper.toDto(consultation);
        return consultationDtos;
    }

    @Override
    public ConsultationDto findConsultationById(Long id) {
        ConsultationEntity csEntity= consultaRepository.findById(id).orElse(null);
        return consultationMapper.toDto(csEntity);
    }

    @Override
    public ConsultationDto saveConsultation(ConsultationRequest consultationRequest) {
        ConsultationEntity csEntity= consultationMapper.toEntity(consultationRequest);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        csEntity.setServiceDate(LocalDateTime.parse(formatter.format(LocalDateTime.now()), formatter));
        csEntity.setFinalTime(LocalTime.now());

        Long id= consultationRequest.history().patient().id();
        PatientEntity patient=patientRepository.findById(id).orElse(null);
        patient.getHistory().addConsultations(csEntity);

        consultaRepository.save(csEntity);
        patientRepository.save(patient);
        return consultationMapper.toDto(csEntity);
    }

    @Override
    public ConsultationDto updateConsultation(ConsultationRequest consultationRequest,Long id) {
        ConsultationEntity consultationUpdate= consultationMapper.toEntity(consultationRequest);
        consultationUpdate.setId(id);

        Long idPatient= consultationRequest.history().patient().id();
        PatientEntity patient=patientRepository.findById(idPatient).orElse(null);
        patient.getHistory().addConsultations(consultationUpdate);

        patientRepository.save(patient);
        consultaRepository.save(consultationUpdate);
        return consultationMapper.toDto(consultationUpdate);
    }

    @Override
    public void deleteConsultationById(Long id) {
        consultaRepository.deleteById(id);
    }
}
