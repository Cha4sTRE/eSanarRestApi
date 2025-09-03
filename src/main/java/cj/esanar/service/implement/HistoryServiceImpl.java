package cj.esanar.service.implement;

import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.persistence.repository.HistoriaRepository;
import cj.esanar.service.HistoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HistoryServiceImpl implements HistoriaService {

    private final HistoriaRepository historiaRepository;

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
