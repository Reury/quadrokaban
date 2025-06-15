package com.reury.kabanquadro.quadrokaban;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("ci") // ADICIONE ESTA LINHA
class QuadrokabanApplicationTests {

	@Test
	void contextLoads() {
	}

}
