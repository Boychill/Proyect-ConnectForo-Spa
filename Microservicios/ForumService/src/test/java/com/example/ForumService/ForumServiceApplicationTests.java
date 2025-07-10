package com.example.ForumService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // <- activa el archivo application-test.properties
class ForumServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}
