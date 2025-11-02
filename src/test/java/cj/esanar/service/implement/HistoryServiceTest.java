package cj.esanar.service.implement;

import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.persistence.repository.HistoryRepository;
import cj.esanar.service.dtos.out.HistoryDto;
import cj.esanar.util.HistoryMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static cj.esanar.dataProviders.ConsultationDataProvider.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoryServiceTest {

    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private HistoryMapper historyMapper;

    @InjectMocks
    private HistoryServiceImpl historyService;

    // ----------- TEST: Listar historias -----------
    @DisplayName("Test para listar todas las historias clínicas")
    @Test
    void testListHistory() {
        // Given
        HistoryEntity history1= history();
        HistoryEntity history2= history();

        List<HistoryEntity> entityList = List.of(history1, history2);

       HistoryDto dto1 = historyDto();
       HistoryDto dto2 = historyDto();

        List<HistoryDto> dtoList = List.of(dto1, dto2);

        when(historyRepository.findAll()).thenReturn(entityList);
        when(historyMapper.toHistoryDto(entityList)).thenReturn(dtoList);

        // When
        List<HistoryDto> result = historyService.listHistory();

        // Then
        assertThat(result).isNotNull().hasSize(2);
        assertThat(result.getFirst().getCreationDate()).isEqualTo(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        verify(historyRepository).findAll();
        verify(historyMapper).toHistoryDto(entityList);
    }

    // ----------- TEST: Buscar historia por ID -----------
    @DisplayName("Test para buscar una historia clínica por ID existente")
    @Test
    void testFindHistoryById_ExistingId() {
        // Given
        HistoryEntity entity = history();

        HistoryDto dto = historyDto();

        when(historyRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(historyMapper.toHistoryDto(entity)).thenReturn(dto);

        // When
        HistoryDto result = historyService.findHistoryById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCreationDate()).isEqualTo(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        verify(historyRepository).findById(1L);
        verify(historyMapper).toHistoryDto(entity);
    }


}
