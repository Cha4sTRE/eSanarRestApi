package cj.esanar.util;

import cj.esanar.persistence.entity.auth.RoleEntity;
import cj.esanar.persistence.entity.auth.UserEntity;
import cj.esanar.service.dtos.in.auth.AuthCreateUserRequest;
import cj.esanar.service.dtos.out.UsersDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    @Mapping(source = "roles",target = "rolesName")
    UsersDto toUserDto(UserEntity userEntity);
    List<UsersDto> toUsersDto(List<UserEntity> userEntities);
    UserEntity toUserEntity(AuthCreateUserRequest authCreateUserRequest);

    default List<String> mapToRolesName(Set<RoleEntity> roles) {
        List<String> roleNames = new ArrayList<>();
        roles.forEach(role -> roleNames.add(role.getName().name()));
        return roleNames;
    }
}
