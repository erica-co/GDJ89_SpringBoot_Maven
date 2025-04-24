package com.winter.app.security;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecurityLoginFailHandler implements AuthenticationFailureHandler{

	
	@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			// TODO Auto-generated method stub
			
			log.info("Fail : {}", exception);
			String message="";
			
			if(exception instanceof BadCredentialsException) {
				message="비밀번호가 틀렸습니다";
			}else if(exception instanceof AccountExpiredException) {
				// 사용자 유효기간이만료
				message=exception.getMessage();
			}else if(exception instanceof LockedException) {
				// 계정 잠김
			}else if(exception instanceof CredentialsExpiredException) {
				// 비번 유효기간 종료
			}else if(exception instanceof DisabledException) {
				// 계정 사용 불가
			}else {
				// 없는 사용자
			}
			message=exception.getMessage();
			message="user.login.password";
			message=URLEncoder.encode(message, "UTF-8");
			
			//redirect
			response.sendRedirect("/user/login?message=".concat(message));
			
			/*
			 * request.setAttribute("code", message); RequestDispatcher view =
			 * request.getRequestDispatcher("/WEB-INF/views/user/login.jsp");
			 * view.forward(request, response);
			 */
			//jsp로 보내는 거라서 파라미터에 message 어쩌고 안뜸
		
		}
}
