package cj.esanar.controller;

import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.service.HistoryService;
import cj.esanar.service.dtos.out.HistoryDto;
import cj.esanar.service.implement.HistoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static cj.esanar.dataProviders.ConsultationDataProvider.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HistoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class HistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class HistoryControllerTestContext{
        @Bean
        HistoryService historyService(){
            return Mockito.mock(HistoryServiceImpl.class);
        }
    }

    @Test
    void findAllStories() throws Exception {
        List<HistoryDto> historyEntityList = List.of(historyDto());
        when(historyService.listHistory()).thenReturn(historyEntityList);
        mockMvc.perform(get("/esanar/api/v1/stories/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].creationDate").value(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
    }

    @Test
    void findHistoryById() throws Exception {

        HistoryDto historyDto = historyDto();
        when(historyService.findHistoryById(anyLong())).thenReturn(historyDto);
        mockMvc.perform(get("/esanar/api/v1/stories/find/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.creationDate").value(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

    }
}