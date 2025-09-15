package cj.esanar.service.dtos.out;


import lombok.Data;

import java.util.Set;

@Data
public class HistoryDto {

    private String creationDate;
    private Set<ConsultationDto> consultations;

}
