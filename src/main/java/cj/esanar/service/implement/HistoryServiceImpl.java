package cj.esanar.service.implement;

import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.persistence.repository.HistoryRepository;
import cj.esanar.service.HistoryService;
import cj.esanar.service.dtos.out.HistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historiaRepository;

    @Override
    public ResponseEntity<List<HistoryDto>> listHistory() {
        return null;
    }

    @Override
    public ResponseEntity<HistoryDto> findHistoryById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<HistoryDto> saveHistory(HistoryEntity historia) {
        return null;
    }
}
