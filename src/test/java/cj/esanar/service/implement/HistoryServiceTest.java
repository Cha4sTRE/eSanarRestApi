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
import java.util.List;
import java.util.Optional;

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
        HistoryEntity history1 = new HistoryEntity();
        history1.setId(1L);
        history1.setCreationDate(LocalDate.of(2024, 5, 10));

        HistoryEntity history2 = new HistoryEntity();
        history2.setId(2L);
        history2.setCreationDate(LocalDate.of(2024, 6, 15));

        List<HistoryEntity> entityList = List.of(history1, history2);

        HistoryDto dto1 = new HistoryDto();
        dto1.setCreationDate("10-05-2024");

        HistoryDto dto2 = new HistoryDto();
        dto2.setCreationDate("15-06-2024");

        List<HistoryDto> dtoList = List.of(dto1, dto2);

        when(historyRepository.findAll()).thenReturn(entityList);
        when(historyMapper.toHistoryDto(entityList)).thenReturn(dtoList);

        // When
        List<HistoryDto> result = historyService.listHistory();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCreationDate()).isEqualTo("10-05-2024");

        verify(historyRepository).findAll();
        verify(historyMapper).toHistoryDto(entityList);
    }

    // ----------- TEST: Buscar historia por ID -----------
    @DisplayName("Test para buscar una historia clínica por ID existente")
    @Test
    void testFindHistoryById_ExistingId() {
        // Given
        HistoryEntity entity = new HistoryEntity();
        entity.setId(1L);
        entity.setCreationDate(LocalDate.of(2024, 5, 10));

        HistoryDto dto = new HistoryDto();
        dto.setCreationDate("10-05-2024");

        when(historyRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(historyMapper.toHistoryDto(entity)).thenReturn(dto);

        // When
        HistoryDto result = historyService.findHistoryById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCreationDate()).isEqualTo("10-05-2024");

        verify(historyRepository).findById(1L);
        verify(historyMapper).toHistoryDto(entity);
    }

    
}
