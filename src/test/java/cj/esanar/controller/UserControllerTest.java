package cj.esanar.controller;

import cj.esanar.service.UserService;
import cj.esanar.service.dtos.in.auth.AuthCreateUserRequest;
import cj.esanar.service.dtos.in.auth.AuthResponse;
import cj.esanar.service.dtos.out.UsersDto;
import com.fasterxml.jackson.core.JsonProcessingException;
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


import java.util.List;

import static cj.esanar.dataProviders.UserDetailsDataProvider.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class UserControllerTestContextConfiguration{
        @Bean
        UserService userService(){return Mockito.mock(UserService.class);}
    }

    @DisplayName("Test para traer la lista de usuarios con GET")
    @Test
    void getAllUsers() throws Exception {

        List<UsersDto> listUsers= List.of(userDtoAdmin(),userDtoAdmin(),userDtoAdmin());

        when(userService.listUsers()).thenReturn(listUsers);

        mockMvc.perform(get("/esanar/api/v1/users/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].name").value("Jefferson"));

    }

    @Test
    void findUserById() throws Exception {

        UsersDto userDto=userDtoAdmin();
        when(userService.getUserById(1L)).thenReturn(userDto);
        mockMvc.perform(get("/esanar/api/v1/users/user/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.name").value("Jefferson"));


    }

    @Test
    void testFindUserById() throws Exception {

        UsersDto userDto=userDtoAdmin();
        when(userService.getUserByUsername("jeffer")).thenReturn(userDto);
        mockMvc.perform(get("/esanar/api/v1/users/username/jeffer"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.username").value("jeffer"));



    }

    @Test
    void updateUser() throws Exception {

        AuthCreateUserRequest authCreateUserRequest= createUserRequest();
        AuthResponse authResponse= new AuthResponse("angelica","user update","fake-token",true);
        long id=1L;

        when(userService.updateUser(authCreateUserRequest,id)).thenReturn(authResponse);
        mockMvc.perform(put("/esanar/api/v1/users/update/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authCreateUserRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(authResponse.message()));

    }
}