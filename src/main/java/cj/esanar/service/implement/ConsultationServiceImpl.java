package cj.esanar.service.implement;

import cj.esanar.persistence.entity.ConsultaEntity;
import cj.esanar.persistence.repository.ConsultaRepository;
import cj.esanar.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultaRepository consultaRepository;

    @Override
    public Set<ConsultaEntity> listConsultations() {
        return (Set<ConsultaEntity>) consultaRepository.findAll();
    }

    @Override
    public Page<ConsultaEntity> listConsultations(Pageable pageable,Long id) {
        return consultaRepository.findAllById(pageable,id);
    }

    @Override
    public Page<ConsultaEntity> listConsultations(Pageable pageable,Long id, String filtros) {
        return consultaRepository.findAllById(pageable,id, filtros);
    }

    @Override
    public void saveConsultation(ConsultaEntity consulta) {
        consultaRepository.save(consulta);
    }

    @Override
    public ConsultaEntity findConsultationtById(ConsultaEntity consulta) {
        return consultaRepository.findById(consulta.getId()).orElse(null);
    }

}
