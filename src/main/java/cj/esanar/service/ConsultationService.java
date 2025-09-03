package cj.esanar.service;

import cj.esanar.persistence.entity.ConsultaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;


public interface ConsultationService {

   Set<ConsultaEntity> listConsultations();

   Page<ConsultaEntity> listConsultations(Pageable pageable, Long id);
   Page<ConsultaEntity> listConsultations(Pageable pageable,Long id,String filtros);
   void saveConsultation(ConsultaEntity consulta);
   ConsultaEntity findConsultationtById(ConsultaEntity consulta);

}
