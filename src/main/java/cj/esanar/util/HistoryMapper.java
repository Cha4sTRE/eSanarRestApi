package cj.esanar.util;

import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.service.dtos.out.HistoryDto;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    HistoryDto toHistoryDto(HistoryEntity history);
    List<HistoryDto> toHistoryDto(List<HistoryEntity> history);

    default String localDateToString(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date!=null?date.format(formatter):"";
    }

}
