package cj.esanar.service.dtos.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorsDto {
    private Integer code;
    private String field;
    private String message;

}
