package cj.esanar.service.dtos.out;

import lombok.Data;

@Data
public class UserDto {

    Long identifier;
    String name;
    String lastName;
    String username;
    String email;
    Long phoneNumber;

}
