package cj.esanar.service.dtos;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class HistoryDto {

    private LocalDate creationDate;
    private PatientDto patient;
    private Set<ConsultationDto> consultations = new HashSet<>();

}
