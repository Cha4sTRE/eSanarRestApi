package cj.esanar.service.dtos.in;


import cj.esanar.persistence.entity.enums.BloodType;
import cj.esanar.persistence.entity.enums.DocumentType;
import cj.esanar.persistence.entity.enums.Gender;
import jakarta.validation.constraints.*;

public record PatientRequest(

        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        String firstName,

        @NotBlank(message = "El apellido no puede estar vacío")
        String lastName,

        @NotNull(message = "El tipo de documento es obligatorio")
        DocumentType documentType,

        @NotBlank(message = "La identificación es obligatoria")
        @Positive(message = "La identificación debe ser un número positivo")
        @Digits(integer = 15, fraction = 0, message = "La identificación debe tener máximo 15 dígitos")
        Long identification,

        @Positive(message = "El teléfono debe ser un número positivo")
        @Digits(integer = 10, fraction = 0, message = "El teléfono debe tener entre 7 y 10 dígitos")
        Long phoneNumber,

        @Size(min = 5, max = 100, message = "La dirección debe tener entre 5 y 100 caracteres")
        String address,

        @Size(min = 2, max = 50, message = "El barrio debe tener entre 2 y 50 caracteres")
        String neighborhood,

        @NotBlank(message = "La fecha de nacimiento es obligatoria")
        @Pattern(
                regexp = "^\\d{2}-\\d{2}-\\d{4}$",
                message = "La fecha de nacimiento debe tener el formato dd-MM-yyyy"
        )
        String birthDate,

        int age,

        @NotNull(message = "El género es obligatorio")
        Gender gender,

        @NotNull(message = "El tipo de sangre es obligatorio")
        BloodType bloodType,

        @Email(message = "El correo electrónico debe tener un formato válido")
        @Size(max = 100, message = "El correo no puede tener más de 100 caracteres")
        String email,

        @Size(min = 2, max = 50, message = "La ocupación debe tener entre 2 y 50 caracteres")
        String occupation,

        @Size(min = 2, max = 50, message = "La EPS debe tener entre 2 y 50 caracteres")
        String eps,

        @NotNull(message = "El estado civil es obligatorio")
        String maritalStatus
) {}
