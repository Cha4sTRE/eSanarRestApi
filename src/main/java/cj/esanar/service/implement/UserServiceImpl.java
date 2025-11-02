package cj.esanar.service.implement;

import cj.esanar.persistence.entity.auth.UserEntity;
import cj.esanar.persistence.repository.UserRepository;
import cj.esanar.service.UserService;
import cj.esanar.service.dtos.in.auth.AuthCreateUserRequest;
import cj.esanar.service.dtos.in.auth.AuthResponse;
import cj.esanar.service.dtos.out.UsersDto;
import cj.esanar.service.implement.security.UserDetailServiceImpl;
import cj.esanar.util.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailServiceImpl userDetailService;
    private final UsersMapper usersMapper;

    @Override
    public List<UsersDto> listUsers() {

        List<UserEntity> entityList=(List<UserEntity>) userRepository.findAll();
        return usersMapper.toUsersDto(entityList);

    }

    @Override
    public UsersDto getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        return usersMapper.toUserDto(userEntity);
    }

    @Override
    public UsersDto getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElse(null);
        return usersMapper.toUserDto(userEntity);
    }

    @Override
    public AuthResponse updateUser(AuthCreateUserRequest authCreateUserRequest,long id) {
        AuthResponse authResponse= userDetailService.updateUser(authCreateUserRequest,id);
        UserEntity userEntity = usersMapper.toUserEntity(authCreateUserRequest);
        return authResponse;
    }
}
