package cj.esanar.service.implement.security;

import cj.esanar.persistence.entity.auth.RoleEntity;
import cj.esanar.persistence.entity.auth.UserEntity;
import cj.esanar.persistence.repository.RoleRepository;
import cj.esanar.persistence.repository.UserRepository;
import cj.esanar.service.dtos.in.auth.AuthCreateUserRequest;
import cj.esanar.service.dtos.in.auth.AuthLoginRequest;
import cj.esanar.service.dtos.in.auth.AuthResponse;
import cj.esanar.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity usuario= userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
        List<SimpleGrantedAuthority> authorityList= new ArrayList<>();

        usuario.getRoles().forEach(role->
                authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName().name()))));

        usuario.getRoles().stream()
                .flatMap(role->role.getListaPermisos().stream())
                .forEach(permission->authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(usuario.getUsername(),usuario.getPassword(),authorityList);

    }

    public AuthResponse loginUser(@Valid AuthLoginRequest loginRequest){

        String username= loginRequest.username();
        String password= loginRequest.password();

        Authentication authentication= this.authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String acccesToken= jwtUtil.generateToken(authentication);

        AuthResponse authResponse= new AuthResponse(username,"user login",acccesToken,true);
        return authResponse;


    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails= this.loadUserByUsername(username);

        if (userDetails==null) throw new BadCredentialsException("Invalid username or password");
        if(!passwordEncoder.matches(password,userDetails.getPassword())) throw new BadCredentialsException("Invalid password");

        return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());

    }

    public AuthResponse createUser(@Valid AuthCreateUserRequest createUserRequest){

        String name= createUserRequest.name();
        String lastName= createUserRequest.lastName();
        String email= createUserRequest.email();
        Long phone= createUserRequest.phone();
        Long identifier= createUserRequest.identifier();
        String username= createUserRequest.username();
        String password= createUserRequest.password();
        List<String> roleList= createUserRequest.roleRequest().roleList();

        Set<RoleEntity> roleEntities= roleRepository.findRoleEntitiesByNameIn(roleList).stream().collect(Collectors.toSet());

        UserEntity usuario=UserEntity.builder()
                .name(name)
                .lastName(lastName)
                .username(username)
                .email(email)
                .identifier(identifier)
                .phoneNumber(phone)
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roleEntities)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .build();

        UserEntity usuarioEntity= userRepository.save(usuario);
        List<SimpleGrantedAuthority> authorityList= new ArrayList<>();

        usuarioEntity.getRoles().stream().forEach(role->
                authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName().name()))));

        usuarioEntity.getRoles().stream().flatMap(role->role.getListaPermisos().stream())
                .forEach(permission->authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        Authentication authentication= new UsernamePasswordAuthenticationToken(usuarioEntity,null,authorityList);
        String accessToken= jwtUtil.generateToken(authentication);
        AuthResponse authResponse= new AuthResponse(username,"user created",accessToken,true);
        return authResponse;

    }

}
