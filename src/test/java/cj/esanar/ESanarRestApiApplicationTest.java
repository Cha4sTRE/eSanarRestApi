package cj.esanar;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ESanarRestApiApplication.class)
@ActiveProfiles("test")
public class ESanarRestApiApplicationTest {

    @Test
    void contextLoads() {
    }

}
