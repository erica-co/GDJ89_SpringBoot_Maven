package com.winter.app.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UserTest {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Test
	void test() throws Exception{
		UserVO userVO = new UserVO();
		userVO.setUsername("admin");
		
		userVO = userDAO.detail(userVO);
		
		String pw = "12345678";
		
		boolean result = passwordEncoder.matches(pw, userVO.getPassword());
		
		assertTrue(result);
	}

}




