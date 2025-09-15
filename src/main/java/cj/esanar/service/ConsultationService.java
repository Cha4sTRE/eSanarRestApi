package cj.esanar.service;

import cj.esanar.service.dtos.in.ConsultationRequest;
import cj.esanar.service.dtos.out.ConsultationDto;

import java.util.List;


public interface ConsultationService {

   List<ConsultationDto> listConsultations();
   ConsultationDto findConsultationById(Long id);

    ConsultationDto saveConsultation(ConsultationRequest consultationRequest);

}
