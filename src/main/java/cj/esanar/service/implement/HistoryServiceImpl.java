package cj.esanar.service.implement;

import cj.esanar.persistence.entity.HistoriaEntity;
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
    public List<HistoriaEntity> listHistory() {
        return (List<HistoriaEntity>) historiaRepository.findAll();
    }

    @Override
    public void saveHistory(HistoriaEntity historia) {
        historiaRepository.save(historia);
    }

    @Override
    public HistoriaEntity findHistoryById(Long id) {
        return historiaRepository.findById(id).orElse(null);
    }

}
