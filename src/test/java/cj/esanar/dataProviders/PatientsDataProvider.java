package cj.esanar.dataProviders;

import cj.esanar.persistence.entity.HistoryEntity;
import cj.esanar.persistence.entity.PatientEntity;
import cj.esanar.persistence.entity.enums.BloodType;
import cj.esanar.persistence.entity.enums.DocumentType;
import cj.esanar.persistence.entity.enums.Gender;
import cj.esanar.persistence.entity.enums.MaritalStatus;
import cj.esanar.service.dtos.in.PatientRequest;
import cj.esanar.service.dtos.out.PatientDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class PatientsDataProvider {

    private static PatientEntity patient1;
    private static PatientEntity patient2;
    private static PatientEntity patient3;
    private static PatientDto dto1;
    private static PatientDto dto2;
    private static PatientDto dto3;
    private static PatientRequest patientRequest;
    private static List<PatientDto> patientDtoList= new ArrayList<>();
    private static List<PatientEntity> patientList= new ArrayList<>();
    static{
        patient1= PatientEntity.builder()
                .documentType(DocumentType.CC)
                .identification(1092524589L)
                .firstName("jefferson")
                .lastName("Chaustre")
                .age(20)
                .birthDate(LocalDate.of(2004,02,20))
                .bloodType(BloodType.O_POS)
                .gender(Gender.Male)
                .maritalStatus(MaritalStatus.Soltero)
                .email("chaustrejefferson@gmail.com")
                .phoneNumber(3166846822L)
                .eps("Sanidadd")
                .address("calle 15")
                .neighborhood("Niza")
                .occupation("Estudiante")
                .build();
        patient2= PatientEntity.builder()
                .documentType(DocumentType.CC)
                .identification(1092524589L)
                .firstName("jefferson")
                .lastName("Chaustre")
                .age(20)
                .birthDate(LocalDate.of(2004,02,20))
                .bloodType(BloodType.O_POS)
                .gender(Gender.Male)
                .maritalStatus(MaritalStatus.Soltero)
                .email("chaustrejefferson@gmail.com")
                .phoneNumber(3166846822L)
                .eps("Sanidadd")
                .address("calle 15")
                .history(HistoryEntity.builder().build())
                .neighborhood("Niza")
                .occupation("Estudiante")
                .history(HistoryEntity.builder().build())
                .build();
        patient3= PatientEntity.builder()
                .documentType(DocumentType.CC)
                .identification(1092524589L)
                .firstName("jefferson")
                .lastName("Chaustre")
                .age(20)
                .birthDate(LocalDate.of(2004,02,20))
                .bloodType(BloodType.O_POS)
                .gender(Gender.Male)
                .maritalStatus(MaritalStatus.Soltero)
                .email("chaustrejefferson@gmail.com")
                .phoneNumber(3166846822L)
                .eps("Sanidadd")
                .address("calle 15")
                .neighborhood("Niza")
                .occupation("Estudiante")
                .build();
        patientRequest=new PatientRequest("Jefferson","Chaustre",
                DocumentType.CC,1092524589L,3166846822L,"calle 15","Niza",
                "20-02-2025",20, Gender.Male, BloodType.O_POS,"chaustrejefferson@gmail.com",
                "estudiante","sanidad", MaritalStatus.Soltero);
        dto1 = new PatientDto();
        dto1.setDocumentType(DocumentType.CC.name());
        dto1.setIdentification(1092524589L);
        dto1.setFirstName("jefferson");
        dto1.setLastName("Chaustre");
        dto1.setAge(20);
        dto1.setBirthDate(LocalDate.of(2004, 2, 20).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto1.setBloodType(BloodType.O_POS.name());
        dto1.setGender(Gender.Male.name());
        dto1.setMaritalStatus(MaritalStatus.Soltero.name());
        dto1.setEmail("chaustrejefferson@gmail.com");
        dto1.setPhoneNumber(3166846822L);
        dto1.setEps("Sanidadd");
        dto1.setAddress("calle 15");
        dto1.setNeighborhood("Niza");
        dto1.setOccupation("Estudiante");
        dto2 = new PatientDto();
        dto2.setDocumentType(DocumentType.CC.name());
        dto2.setIdentification(1093584758L);
        dto2.setFirstName("jefferson");
        dto2.setLastName("Chaustre");
        dto2.setAge(20);
        dto2.setBirthDate(LocalDate.of(2004, 2, 20).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto2.setBloodType(BloodType.O_POS.name());
        dto2.setGender(Gender.Male.name());
        dto2.setMaritalStatus(MaritalStatus.Soltero.name());
        dto2.setEmail("chaustrejefferson@gmail.com");
        dto2.setPhoneNumber(3166846822L);
        dto2.setEps("Sanidadd");
        dto2.setAddress("calle 15");
        dto2.setNeighborhood("Niza");
        dto2.setOccupation("Estudiante");
        dto3 = new PatientDto();
        dto3.setDocumentType(DocumentType.CC.name());
        dto3.setIdentification(1093584758L);
        dto3.setFirstName("jefferson");
        dto3.setLastName("Chaustre");
        dto3.setAge(20);
        dto3.setBirthDate(LocalDate.of(2004, 2, 20).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dto3.setBloodType(BloodType.O_POS.name());
        dto3.setGender(Gender.Male.name());
        dto3.setMaritalStatus(MaritalStatus.Soltero.name());
        dto3.setEmail("chaustrejefferson@gmail.com");
        dto3.setPhoneNumber(3166846822L);
        dto3.setEps("Sanidadd");
        dto3.setAddress("calle 15");
        dto3.setNeighborhood("Niza");
        dto3.setOccupation("Estudiante");
    }
    public static PatientEntity patient1(){
        return patient1;
    }
    public static PatientEntity patient2(){
        return patient2;
    }
    public static PatientEntity patient3(){
        return patient3;
    }
    public static PatientDto dto1(){
        return dto1;
    }
    public static PatientDto dto2(){
        return dto2;
    }
    public static PatientDto dto3(){
        return dto3;
    }
    public static PatientRequest patientRequest(){return patientRequest;}
    public static List<PatientEntity> patientList(){
        patientList= List.of(patient1,patient2,patient3);
        return patientList;
    }
    public static List<PatientDto> patientDtoList(){
        patientDtoList= List.of(dto1,dto2,dto3());
        return patientDtoList;
    }

}
