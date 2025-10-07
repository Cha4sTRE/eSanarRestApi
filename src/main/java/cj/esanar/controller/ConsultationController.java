package cj.esanar.controller;

import cj.esanar.service.ConsultationService;
import cj.esanar.service.dtos.in.ConsultationRequest;
import cj.esanar.service.dtos.out.ConsultationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/esanar/api/v1/consultations")
public class ConsultationController {

    private final ConsultationService consultationService;

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ENF','MEDIC','VISITOR','ADMIN')")
    public ResponseEntity<List<ConsultationDto>> findAllConsultations(){
        List<ConsultationDto> consultationDtos= consultationService.listConsultations();
        return new ResponseEntity<>(consultationDtos, HttpStatus.OK);
    }

    @GetMapping("/consultation/{id}")
    @PreAuthorize("hasAnyRole('ENF','MEDIC','VISITOR','ADMIN')")
    public ResponseEntity<ConsultationDto> findConsultationById(@PathVariable Long id){
        ConsultationDto consultationDto= consultationService.findConsultationById(id);
        return new ResponseEntity<>(consultationDto, HttpStatus.OK);
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('ENF','MEDIC','ADMIN')")
    public ResponseEntity<ConsultationDto> newConsultation(@RequestBody @Valid ConsultationRequest consultationRequest) {

        ConsultationDto consultation= consultationService.saveConsultation(consultationRequest);
        return new ResponseEntity<>(consultation, HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('MEDIC','ADMIN')")
    public ResponseEntity<ConsultationDto> updateConsultation(@PathVariable Long id,
                                                              @RequestBody @Valid ConsultationRequest consultationRequest){
        ConsultationDto update= consultationService.updateConsultation(consultationRequest,id);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteConsultation(@PathVariable Long id){
        consultationService.deleteConsultationById(id);
        return new ResponseEntity<>("patient delete",HttpStatus.OK);
    }
}
