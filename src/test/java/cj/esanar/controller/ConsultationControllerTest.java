package cj.esanar.controller;

import cj.esanar.service.ConsultationService;
import cj.esanar.service.dtos.in.ConsultationRequest;
import cj.esanar.service.dtos.out.ConsultationDto;
import cj.esanar.service.implement.ConsultationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static cj.esanar.dataProviders.ConsultationDataProvider.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        ConsultationService consultationService(){
            return Mockito.mock(ConsultationServiceImpl.class);
        }
    }

    @DisplayName("Test para buscar consultas con GET")
    @Test
    void tesFindAllConsultations() throws Exception {

        List<ConsultationDto> listTest= List.of(consultationDto());
        when(consultationService.listConsultations()).thenReturn(listTest);

        mockMvc.perform(get("/esanar/api/v1/consultations/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].primaryDiagnosis").value("diagnostico de prueba"));

    }
    @DisplayName("Test para buscar consulta por id con GET")
    @Test
    void testFindConsultationById() throws Exception {

        ConsultationDto consultationDto = consultationDto();
        when(consultationService.findConsultationById(anyLong())).thenReturn(consultationDto);
        mockMvc.perform(get("/esanar/api/v1/consultations/consultation/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.primaryDiagnosis").value("diagnostico de prueba"));
    }
    @DisplayName("Test para guardar nueva consulta con POST")
    @Test
    void testNewConsultation() throws Exception {

        ConsultationDto testConsultationSave = consultationDto();
        ConsultationRequest consultationRequest = consultationRequest();
        testConsultationSave.setMotive(consultationRequest.motive());

        when(consultationService.saveConsultation(consultationRequest)).thenReturn(testConsultationSave);

        mockMvc.perform(post("/esanar/api/v1/consultations/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(consultationRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.motive").value("prueba de nueva consulta"))
                .andExpect(jsonPath("$.serviceDate").value(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
                .andExpect(jsonPath("$.finalTime").value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));

    }

    @DisplayName("Test para actualizar una consulta con PUT")
    @Test
    void updateConsultation() throws Exception {

        ConsultationDto testConsultationSave = consultationDto();
        ConsultationRequest consultationRequest = consultationRequestUpdate();
        testConsultationSave.setMotive(consultationRequest.motive());

        when(consultationService.updateConsultation(consultationRequest,1L)).thenReturn(testConsultationSave);

        mockMvc.perform(put("/esanar/api/v1/consultations/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consultationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.motive").value("prueba de actualizar consulta"))
                .andExpect(jsonPath("$.serviceDate").value(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
                .andExpect(jsonPath("$.finalTime").value(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));

    }

    @DisplayName("Test para eliminar una consulta con DELETE")
    @Test
    void deleteConsultation() throws Exception {

        Long id = 1L;
        doNothing().when(consultationService).deleteConsultationById(id);
        mockMvc.perform(delete("/esanar/api/v1/consultations/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("patient delete"));

    }
}