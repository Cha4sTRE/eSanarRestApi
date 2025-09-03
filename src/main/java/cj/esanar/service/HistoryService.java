package cj.esanar.service;

import cj.esanar.persistence.entity.HistoriaEntity;

import java.util.List;

public interface HistoryService {

    List<HistoriaEntity> listHistory();
    HistoriaEntity findHistoryById(Long id);
    void saveHistory(HistoriaEntity historia);


}
