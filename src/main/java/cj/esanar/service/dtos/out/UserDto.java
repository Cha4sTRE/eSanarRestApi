package cj.esanar.service.dtos.out;

import lombok.Data;

@Data
public class UserDto {

    private Long identifier;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private Long phoneNumber;

}
