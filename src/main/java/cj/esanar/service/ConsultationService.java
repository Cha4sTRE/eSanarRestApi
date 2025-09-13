package cj.esanar.service;

import cj.esanar.persistence.entity.ConsultationEntity;
import cj.esanar.service.dtos.out.ConsultationDto;
import org.springframework.http.ResponseEntity;

import java.util.Set;


public interface ConsultationService {

   ResponseEntity<Set<ConsultationDto>> listConsultations();

   ResponseEntity<ConsultationDto> saveConsultation(ConsultationEntity consulta);
   ResponseEntity<ConsultationDto> findConsultationtById(ConsultationEntity consulta);

}
