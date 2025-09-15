package cj.esanar.service.dtos.out;


import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ConsultationDto {

    private UserDto nurse;
    private String primaryDiagnosis;
    private String motive;
    private Integer length;
    private Integer width;
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
