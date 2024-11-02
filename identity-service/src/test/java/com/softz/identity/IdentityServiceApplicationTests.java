package com.softz.identity;

import com.softz.identity.util.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class IdentityServiceApplicationTests {
	@Autowired
	private JwtService jwtService;

	// @Test
	// void testGenerateToken(){
	// 	log.info("Token: {}",
	// 		jwtService.generateToken("john","12345678"));
	// }

	@Test
	void testVerifyToken(){
		log.info("Result: {}",
				jwtService.verifyToken("eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzb2Z0ei5jb20iLCJzdWIiOiJqb2huIiwiZXhwIjoxNzI5OTIzMjc5LCJ1c2VySWQiOiIxMjM0NTY3OCIsImlhdCI6MTcyOTkyMzI3OX0.vV-T4A58eK1vIU8CymqzE6gvYTZGCNT8VlVlNIN1Ix_yGJDjy6u7KTNBlfBTidZZMvxmohh0M6wqmdegoio8lw"));
	}
}
