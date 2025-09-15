package cj.esanar.service.implement;

import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.persistence.repository.HistoryRepository;
import cj.esanar.service.HistoryService;
import cj.esanar.service.dtos.out.HistoryDto;
import cj.esanar.util.HistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historiaRepository;
    private final HistoryMapper historiaMapper;

    @Override
    public List<HistoryDto> listHistory() {
        List<HistoryEntity> stories= historiaRepository.findAll();
        List<HistoryDto> storiesDtos=historiaMapper.toHistoryDto(stories);
        return storiesDtos;
    }

    @Override
    public HistoryDto findHistoryById(Long id) {
        HistoryEntity historyEntity = historiaRepository.findById(id).orElse(null);
        return historiaMapper.toHistoryDto(historyEntity);
    }
}
