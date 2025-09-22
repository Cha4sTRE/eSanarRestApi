package cj.esanar.util;

import cj.esanar.persistence.entity.ConsultationEntity;
import cj.esanar.persistence.entity.auth.UserEntity;
import cj.esanar.service.dtos.in.ConsultationRequest;
import cj.esanar.service.dtos.out.ConsultationDto;
import cj.esanar.service.dtos.out.UserDto;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {

    ConsultationEntity toEntity(ConsultationRequest consultationRequest);
    ConsultationDto toDto(ConsultationEntity csEntity);
    List<ConsultationDto> toDto(List<ConsultationEntity> csEntity);
    UserDto toUserDto(UserEntity user);

    default String dateToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return date!=null?date.format(formatter):"";
    }
    default String localTimeToString(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return localTime!=null?localTime.format(formatter):"";
    }

}
