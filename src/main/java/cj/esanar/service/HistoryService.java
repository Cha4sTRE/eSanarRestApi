package cj.esanar.service;

import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.service.dtos.out.HistoryDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HistoryService {

    List<HistoryDto> listHistory();
    HistoryDto findHistoryById(Long id);

}
