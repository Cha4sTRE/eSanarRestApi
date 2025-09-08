package cj.esanar.service.implement;

import cj.esanar.persistence.entity.ConsultationEntity;
import cj.esanar.persistence.repository.ConsultationRepository;
import cj.esanar.service.ConsultationService;
import cj.esanar.service.dtos.ConsultationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultaRepository;

    @Override
    public ResponseEntity<Set<ConsultationDto>> listConsultations() {
        return null;
    }

    @Override
    public ResponseEntity<ConsultationDto> saveConsultation(ConsultationEntity consulta) {
        return null;
    }

    @Override
    public ResponseEntity<ConsultationDto> findConsultationtById(ConsultationEntity consulta) {
        return null;
    }
}
