package cj.esanar.service.implement;

import cj.esanar.persistence.entity.EvaluationEntity;
import cj.esanar.persistence.repository.ConsultationRepository;
import cj.esanar.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultaRepository;

    @Override
    public Set<EvaluationEntity> listConsultations() {
        return (Set<EvaluationEntity>) consultaRepository.findAll();
    }


    @Override
    public void saveConsultation(EvaluationEntity consulta) {
        consultaRepository.save(consulta);
    }

    @Override
    public EvaluationEntity findConsultationtById(EvaluationEntity consulta) {
        return consultaRepository.findById(consulta.getId()).orElse(null);
    }

}
