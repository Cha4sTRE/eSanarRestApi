package cj.esanar.controller;

import cj.esanar.dataProviders.ConsultationDataProvider;
import cj.esanar.service.ConsultationService;
import cj.esanar.service.dtos.out.ConsultationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cj.esanar.dataProviders.ConsultationDataProvider.consultationDto;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConsultationController.class)
@AutoConfigureMockMvc(addFilters = false)
class ConsultationControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ConsultationService consultationService;
    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class consultationServiceConfigure{
        @Bean
        public ConsultationService consultationService(){
            return Mockito.mock(ConsultationService.class);
        }
    }
    @DisplayName("Test para buscar consultas con GET")
    @Test
    void tesFindAllConsultations() throws Exception {

        List<ConsultationDto> listTest= List.of(consultationDto());
        when(consultationService.listConsultations()).thenReturn(listTest);

        mockMvc.perform(get("/esanar/api/v1/consultations/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].primaryDiagnosis").value("diagnostico de prueba"));

    }

    @Test
    void testFindConsultationById() {



    }

    @Test
    void newConsultation() {
    }

    @Test
    void updateConsultation() {
    }

    @Test
    void deleteConsultation() {
    }
}