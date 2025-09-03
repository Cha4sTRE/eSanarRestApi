package cj.esanar.service;

import cj.esanar.persistence.entity.HistoryEntity;

import java.util.List;

public interface HistoriaService {

    List<HistoryEntity> listHistory();
    HistoryEntity findHistoryById(Long id);
    void saveHistory(HistoryEntity historia);


}
