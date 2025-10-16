package cj.esanar.service.dtos.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class BadRequestDto {

    private Date timestamp= new Date();
    private List<ErrorsDto> errors;
    private String path;

    public BadRequestDto(List<ErrorsDto> errors, String path) {
        this.errors = errors;
        this.path = path.replace("uri=","");
    }
}
