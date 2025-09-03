package cj.esanar.service.dtos;


import java.time.LocalDateTime;
import java.time.LocalTime;

public class ConsultationDto {


    private HistoryDto medicalHistory;
    private UserDto nurse;
    private String mainDiagnosis;
    private String consultationReason;
    private Integer length;
    private Integer width;
    private String depth;
    private String shape;
    private String smell;
    private String woundEdges;
    private String infection;
    private String exudateType;
    private String exudateLevel;
    private LocalDateTime careDateTime;
    private LocalTime endTime;


}
