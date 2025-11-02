package cj.esanar.service.dtos.out;

import lombok.Data;

import java.util.List;

@Data
public class UsersDto {

    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private Long identifier;
    private Long phoneNumber;
    private List<String> rolesName;

    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;

}
