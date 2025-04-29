package com.winter.app.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.winter.app.user.UserDAO;
import com.winter.app.user.UserVO;
import com.winter.app.websocket.LoginUsers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecurityLoginSuccessHandler implements AuthenticationSuccessHandler{
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// username은 authentication에 담겨있음
		
		log.info("Login Success : {}", authentication);
		
		UserVO userVO = (UserVO)authentication.getPrincipal();
		userVO.setStatus(true); //true면 1로 바뀜 -> 로그아웃 0, 로그인 1
		
		try {
			int result = userDAO.statusChange(userVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LoginUsers.USERNAMES.add(authentication.getName());
		
		
		//forward
		/*
		 * RequestDispatcher view =
		 * request.getRequestDispatcher("/WEB-INF/views/user/login");
		 * view.forward(request, response);
		 */
		
		//redirect
		response.sendRedirect("/");
		
	}
}
