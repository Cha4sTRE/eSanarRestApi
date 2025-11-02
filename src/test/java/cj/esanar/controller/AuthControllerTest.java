package cj.esanar.controller;

import cj.esanar.dataProviders.UserDetailsDataProvider;
import cj.esanar.service.dtos.in.auth.AuthCreateUserRequest;
import cj.esanar.service.dtos.in.auth.AuthLoginRequest;
import cj.esanar.service.dtos.in.auth.AuthResponse;
import cj.esanar.service.implement.security.UserDetailServiceImpl;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserDetailServiceImpl userDetailsService;
    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class userDetailsServiceConfigure {
        @Bean
        UserDetailServiceImpl userDetailsService() {
            return Mockito.mock(UserDetailServiceImpl.class);
        }
    }
    @DisplayName("Test para login de usuario con POST")
    @Test
    void testLogin() throws Exception {

        AuthLoginRequest authLoginRequest = new AuthLoginRequest("jeffer","fake-password");
        AuthResponse authResponse = new AuthResponse("jeffer","user login","fake-token",true);

        when(userDetailsService.loginUser(authLoginRequest)).thenReturn(authResponse);

        mockMvc.perform(post("/esanar/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authLoginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("user login"))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.username").value("jeffer"));

    }
    @DisplayName("Test para registro de usuario con POST")
    @Test
    void register()  throws Exception {

        AuthCreateUserRequest authCreateUserRequest = UserDetailsDataProvider.createUserRequest();
        AuthResponse authResponse = new AuthResponse(authCreateUserRequest.username(),"user created","fake-token",true);

        when(userDetailsService.createUser(authCreateUserRequest)).thenReturn(authResponse);

        mockMvc.perform(post("/esanar/api/v1/auth/sign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authCreateUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("user created"))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.username").value(authCreateUserRequest.username()));

    }
}