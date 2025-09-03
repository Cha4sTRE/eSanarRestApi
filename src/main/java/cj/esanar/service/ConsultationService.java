package cj.esanar.service;

import cj.esanar.persistence.entity.EvaluationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;


public interface ConsultationService {

   Set<EvaluationEntity> listConsultations();

   void saveConsultation(EvaluationEntity consulta);
   EvaluationEntity findConsultationtById(EvaluationEntity consulta);

}
