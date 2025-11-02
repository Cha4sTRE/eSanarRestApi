package cj.esanar.exception;

import cj.esanar.service.dtos.exceptions.BadRequestDto;
import cj.esanar.service.dtos.exceptions.ErrorsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadRequestDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                   WebRequest request) {

        List<ErrorsDto> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(new ErrorsDto(400,fieldName, errorMessage));
        });
        BadRequestDto badRequestException = new BadRequestDto(errors,request.getDescription(false));
        return ResponseEntity.badRequest().body(badRequestException);
    }


}
