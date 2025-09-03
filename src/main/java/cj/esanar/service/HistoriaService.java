package cj.esanar.service;

import cj.esanar.persistence.entity.HistoriaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HistoriaService {

    List<HistoriaEntity> listHistory();
    HistoriaEntity findHistoryById(Long id);
    void saveHistory(HistoriaEntity historia);


}
