package cj.esanar.controller;

import cj.esanar.service.PatientService;
import cj.esanar.service.dtos.in.PatientRequest;
import cj.esanar.service.dtos.out.PatientDto;
import cj.esanar.service.implement.PatientServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cj.esanar.dataProviders.PatientsDataProvider.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class PatientControllerTestContextConfiguration {
        @Bean
        PatientService patientService() {
            return Mockito.mock(PatientServiceImpl.class);
        }
    }
    @DisplayName("Test para traer la paginacion de pacientes con GET")
    @Test
    void testListPatients() throws Exception {

        //Given
        Page<PatientDto> pagePatient= new PageImpl<>(List.of(dto1(),dto2(),dto3()));
        //When
        when(patientService.listPatients(any(Pageable.class))).thenReturn(pagePatient);
        //Then
        mockMvc.perform(get("/esanar/api/v1/patients/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].firstName").value("jefferson"));

    }
    @DisplayName("Test para buscar paciente con GET")
    @Test
    void testFindPatientById() throws Exception {

        PatientDto patientDto = dto1();
        when(patientService.findPatientsById(anyLong())).thenReturn(patientDto);

        mockMvc.perform(get("/esanar/api/v1/patients/patient/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(patientDto.getFirstName()));

    }
    @DisplayName("Test para crear un nuevo paciente con POST")
    @Test
    void testNewPatients() throws Exception {

        PatientRequest patientRequest = patientRequest();
        PatientDto patientDto = dto1();

        when(patientService.savePatients(patientRequest)).thenReturn(patientDto);

        mockMvc.perform(post("/esanar/api/v1/patients/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(patientDto.getFirstName()));

    }

}