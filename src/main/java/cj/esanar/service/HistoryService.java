package cj.esanar.service;

import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.service.dtos.HistoryDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HistoryService {

    ResponseEntity<List<HistoryDto>> listHistory();
    ResponseEntity<HistoryDto> findHistoryById(Long id);
    ResponseEntity<HistoryDto> saveHistory(HistoryEntity historia);


}
