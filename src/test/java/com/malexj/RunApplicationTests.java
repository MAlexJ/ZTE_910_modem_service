package com.malexj;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RunApplicationTests {

	static {
		System.setProperty("ADMIN_PHONE_NUMBER", "8067777777");
	}

	@Test
	void contextLoads() {
	}

}
