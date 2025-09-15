package cj.esanar.controller;

import cj.esanar.service.ConsultationService;
import cj.esanar.service.dtos.in.ConsultationRequest;
import cj.esanar.service.dtos.out.ConsultationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/esanar/api/v1/consultations")
public class ConsultationController {

    private final ConsultationService consultationService;

    @GetMapping("/list")
    public ResponseEntity<List<ConsultationDto>> findAllConsultations(){
        List<ConsultationDto> consultationDtos= consultationService.listConsultations();
        return new ResponseEntity<>(consultationDtos, HttpStatus.OK);
    }

    @GetMapping("/consultation/{id}")
    public ResponseEntity<ConsultationDto> findConsultationById(@PathVariable Long id){
        ConsultationDto consultationDto= consultationService.findConsultationById(id);
        return new ResponseEntity<>(consultationDto, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<ConsultationDto> newConsultation(@RequestBody @Valid ConsultationRequest consultationRequest) {

        ConsultationDto consultation= consultationService.saveConsultation(consultationRequest);
        return new ResponseEntity<>(consultation, HttpStatus.OK);

    }
}
