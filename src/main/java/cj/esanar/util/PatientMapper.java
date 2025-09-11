package cj.esanar.util;

import cj.esanar.persistence.entity.ConsultationEntity;
import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.persistence.entity.PatientEntity;
import cj.esanar.service.dtos.ConsultationDto;
import cj.esanar.service.dtos.HistoryDto;
import cj.esanar.service.dtos.PatientDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDto toPatientDto(PatientEntity patient);
    HistoryDto toHistoryDto(HistoryEntity history);
    List<PatientDto> toPatientDtos(List<PatientEntity> patients);

}
