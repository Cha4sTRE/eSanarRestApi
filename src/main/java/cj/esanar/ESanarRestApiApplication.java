package cj.esanar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class ESanarRestApiApplication {

    public static void main(String[] args) {SpringApplication.run(ESanarRestApiApplication.class, args);}

}
