package cj.esanar.service.dtos.out;


import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ConsultationDto {

    private UserDto attendedBy;
    private String primaryDiagnosis;
    private String motive;
    private Double length;
    private Double width;
    private String depth;
    private String form;
    private String smell;
    private String woundEdges;
    private String infection;
    private String exudateType;
    private String exudateLevel;
    private String serviceDate;
    private String finalTime;


}
