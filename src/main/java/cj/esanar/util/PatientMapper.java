package cj.esanar.util;

import cj.esanar.persistence.entity.PatientEntity;
import cj.esanar.service.dtos.in.PatientRequest;
import cj.esanar.service.dtos.out.PatientDto;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientEntity toPatient(PatientRequest patient);
    PatientDto toPatientDto(PatientEntity patient);


    default LocalDate mapToLocalDate(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return birthDate.isEmpty()?LocalDate.now():LocalDate.parse(birthDate, formatter);
    }

    default String mapToString(LocalDate birthDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return birthDate!=null?birthDate.format(formatter):"";
    }

}
