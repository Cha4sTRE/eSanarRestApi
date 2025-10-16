package cj.esanar.service.dtos.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthErrorDto {

    String message;
    String path;

}
