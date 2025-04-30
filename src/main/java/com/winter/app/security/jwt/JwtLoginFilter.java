package com.winter.app.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter{
	
	
	private JwtTokenManager jwtTokenManager;
	
	private AuthenticationManager authenticationManager;
	
	//login 요청을 처리
	public JwtLoginFilter(AuthenticationManager authenticationManager, JwtTokenManager jwtTokenManager) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenManager=jwtTokenManager;
		//이 필터를 어떤 경로일 때 실행할 것인지
		this.setFilterProcessesUrl("/users/login");
	
	}
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		log.info("login 시도");
		//jsp 에서 name에 해당하는 값
		//log.info("PASSWORD : {} ", request.getParameter("password"));
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//authentication에는 id, 비번이 있음
		//principal : id , credential : 비번
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		//UserDetails의 loadByUsername을 호출(id, 비번비교까지 다 하고 옴)
		Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
		
		log.info("result : {}", authentication);
		
		return authentication;
	}
	
	//authentication에서 로그인 성공하면 아래 메서드 자동호출됨
	//authentication내에 유저정보있음 
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info("authResult : {}", authResult);
		
		//성공했으니까 Token 생성하고 Client로 전송하자
		String token = jwtTokenManager.createToken(authResult);
		log.info("token : {} ", token);
		
		Cookie cookie = new Cookie("accessToken", token);
		cookie.setMaxAge(120); //120초 
		cookie.setPath("/");
		//cookie.setSecure(true); SSL 통신시에만 쿠키를 저장함
		//cookie.setHttpOnly(true);//자바스크립트에서 쿠키를 변경할 수 없게 함
		response.addCookie(cookie);
		
		response.sendRedirect("/");
		
		//redirect로 보내면 cookie가 저장되지 않고 삭제 되는 현상 발생- forward로 보내기
		//RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/commons/jwtLoginResult.jsp");
		//view.forward(request, response);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info("fail : {}", failed);
		//로그인이 실패했을 때 실행됨
		response.sendRedirect("/user/login");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
