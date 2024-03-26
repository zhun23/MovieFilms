package com.example.dev;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovieFilmsApplicationTests {

	@Test
	void shouldBeAlwaysTrue() {
		assertThat(true).isTrue();
	}

}
