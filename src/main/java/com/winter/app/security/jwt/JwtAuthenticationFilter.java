package com.winter.app.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
	//특정 url이 들어와야 filter 실행 
	
	private JwtTokenManager jwtTokenManager;
	
	//생성자 호출
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenManager jwtTokenManager) {
		super(authenticationManager); //부모의 기본 생성자를 호출하는 코드
		this.jwtTokenManager = jwtTokenManager;
		
	}
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Token의 유무와 Token이 있으면 유효성 검증
		
		
		log.info("Access token 검증");
		
		Cookie [] cookies = request.getCookies();
		
		String accessToken="";
		for(Cookie cookie:cookies) {
			if(cookie.getName().equals("accessToken")) {
				accessToken = cookie.getValue();
				break;
			}
		}
		
		log.info("Access Token : {} ", accessToken);
		
		if(accessToken.length()>0 ) {
			try {
				//token 유효성 검증 
				Claims claims = jwtTokenManager.tokenValidation(accessToken);
				
				Authentication authentication = jwtTokenManager.getauthentication(claims.getSubject());
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		chain.doFilter(request, response);
		//super.doFilterInternal(request, response, chain);
	}
	
}
