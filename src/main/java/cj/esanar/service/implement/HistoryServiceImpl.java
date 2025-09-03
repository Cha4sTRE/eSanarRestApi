package cj.esanar.service.implement;

import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.persistence.repository.HistoryRepository;
import cj.esanar.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historiaRepository;

    @Override
    public List<HistoryEntity> listHistory() {
        return (List<HistoryEntity>) historiaRepository.findAll();
    }

    @Override
    public void saveHistory(HistoryEntity historia) {
        historiaRepository.save(historia);
    }

    @Override
    public HistoryEntity findHistoryById(Long id) {
        return historiaRepository.findById(id).orElse(null);
    }

}
