package cj.esanar.service.implement;

import cj.esanar.persistence.entity.auth.UserEntity;
import cj.esanar.persistence.repository.UserRepository;
import cj.esanar.service.dtos.in.auth.AuthCreateUserRequest;
import cj.esanar.service.dtos.in.auth.AuthResponse;
import cj.esanar.service.dtos.out.UsersDto;
import cj.esanar.service.implement.security.UserDetailServiceImpl;
import cj.esanar.util.UsersMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static cj.esanar.dataProviders.UserDetailsDataProvider.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UsersMapper usersMapper;
    @Mock
    private UserDetailServiceImpl userDetailService;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @DisplayName("Test para buscar lista de usuarios")
    @Test
    void testListUsers() {

        List<UserEntity> userList= List.of(userAdmin(),userAdmin(),userAdmin());
        List<UsersDto> userDtoList= List.of(userDtoAdmin(),userDtoAdmin(),userDtoAdmin());

        when(userRepository.findAll()).thenReturn(userList);
        when(usersMapper.toUsersDto(userList)).thenReturn(userDtoList);
        List<UsersDto> result = userServiceImpl.listUsers();

        assertThat(result).isNotNull().hasSize(userDtoList.size());
        assertThat(result).isEqualTo(userDtoList);
        assertThat(result.getFirst().getRolesName()).isEqualTo(userDtoList.getFirst().getRolesName());

    }

    @DisplayName("Test para buscar usuario por id")
    @Test
    void testGetUserById() {

        UsersDto admin=userDtoAdmin();
        UserEntity userEntity=userAdmin();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
        when(usersMapper.toUserDto(userEntity)).thenReturn(admin);

        UsersDto userDto = userServiceImpl.getUserById(1L);

        assertThat(userDto).isNotNull();
        assertThat(userDto.getId()).isEqualTo(admin.getId());
        assertThat(userDto.getName()).isEqualTo(admin.getName());

    }

    @DisplayName("Test para buscar usuario por usernname")
    @Test
    void testGetUserByUsername() {

        UsersDto admin=userDtoAdmin();
        UserEntity userEntity=userAdmin();

        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));
        when(usersMapper.toUserDto(userEntity)).thenReturn(admin);

        UsersDto result= userServiceImpl.getUserByUsername("jeffer");

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(admin.getUsername());

    }

    @DisplayName("Test para actualizar usuario")
    @Test
    void testUpdateUser() {

        AuthCreateUserRequest authCreateUserRequest= createUserRequest();
        AuthResponse authResponse= new AuthResponse("angelica","user update","fake-token",true);
        long id=1L;

        when(userDetailService.updateUser(authCreateUserRequest,id)).thenReturn(authResponse);

        AuthResponse result= userServiceImpl.updateUser(authCreateUserRequest,id);

        assertThat(result).isNotNull();
        assertThat(result.message()).isEqualTo(authResponse.message());
        assertThat(result.username()).isEqualTo(authResponse.username());


    }
}