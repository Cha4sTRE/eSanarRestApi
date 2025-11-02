package cj.esanar.dataProviders;

import cj.esanar.persistence.entity.auth.ERole;
import cj.esanar.persistence.entity.auth.PermissionEntity;
import cj.esanar.persistence.entity.auth.RoleEntity;
import cj.esanar.persistence.entity.auth.UserEntity;
import cj.esanar.service.dtos.in.auth.AuthCreateRoleRequest;
import cj.esanar.service.dtos.in.auth.AuthCreateUserRequest;

import java.util.List;
import java.util.Set;

public class UserDetailsDataProvider {

    private static PermissionEntity read;
    private static PermissionEntity create;
    private static PermissionEntity update;
    private static PermissionEntity delete;
    private static RoleEntity admin;
    private static UserEntity userAdmin;
    private static AuthCreateUserRequest  createUserRequest;

    static{
        create= PermissionEntity.builder().name("CREATE").build();
        read= PermissionEntity.builder().name("READ").build();
        update= PermissionEntity.builder().name("UPDATE").build();
        delete= PermissionEntity.builder().name("DELETE").build();
        admin= RoleEntity.builder().name(ERole.ADMIN).listaPermisos(Set.of(create,read,update,delete)).build();
        userAdmin= UserEntity.builder()
                .name("Jefferson")
                .lastName("Chaustre")
                .username("jeffer")
                .identifier(1092524589L)
                .phoneNumber(3166846822L)
                .email("chaustrejefferson@gmail.com")
                .password("milluh123")
                .roles(Set.of(admin))
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isAccountNonLocked(true)
                .build();
        createUserRequest= new AuthCreateUserRequest("Luz","Angelica","angelica@gmail.com",
                109584652L,32564856L,"angelica","camila123",new AuthCreateRoleRequest(List.of("ADMIN","MEDIC")));
    }
    public static UserEntity userAdmin(){
        return userAdmin;
    }
    public static RoleEntity roleAdmin(){return admin;}
    public static AuthCreateUserRequest createUserRequest(){return createUserRequest;}
}
