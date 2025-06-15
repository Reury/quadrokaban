package com.reury.kabanquadro.quadrokaban;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("ci")
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:mariadb://mariadb:3306/quadrokaban",
    "spring.datasource.username=devuser",
    "spring.datasource.password=devpass",
    "spring.datasource.driver-class-name=org.mariadb.jdbc.Driver"
})
class QuadrokabanApplicationTests {

    @Test
    void contextLoads() {
    }
}
