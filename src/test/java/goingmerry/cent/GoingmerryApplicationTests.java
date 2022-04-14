package goingmerry.cent;

import goingmerry.cent.jwt.JwtTokenResolver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class GoingmerryApplicationTests {

	@Autowired
	JwtTokenResolver jwtTokenResolver;

	@Test
	void contextLoads() {
		Long id = jwtTokenResolver.getId("F7BZuWsrd06lFUv4pphsGvZHe7ez9SRgQjGASgopb1QAAAGAKJFmMw");
		System.out.println(id);
	}

}
