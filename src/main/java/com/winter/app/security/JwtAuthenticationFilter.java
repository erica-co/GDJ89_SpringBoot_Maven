package com.winter.app.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter{
	
	private JwtTokenManager jwtTokenManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenManager jwtTokenManager) {
		super(authenticationManager);
		
		this.jwtTokenManager = jwtTokenManager;
	}
	
	//token 검증
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		log.info("토큰 검증");
		
		String token = request.getHeader("Authorization");
		
		log.info(token);
		if(token != null && token.startsWith("Bearer ")) {//띄어쓰기까지 포함 
			token = token.substring(token.indexOf(" ")+1); 
			//0번부터 시작해서 띄어쓰기 여백까지하면 6이니까 7번부터여야 하고
			//여백에서 그 다음꺼부터 가져와라 (토큰)
			try {
				//Token 유효성 검증
				Claims claims = jwtTokenManager.tokenValidation(token);
				
				Authentication authentication = jwtTokenManager.getAuthentication(claims.getSubject());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				
				
								
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				//accessToken이 만료되었을 때 catch에서 잡아줘야함 
				if(e instanceof ExpiredJwtException) {}
				token = request.getHeader("RefreshToken");
				log.info("refresh{}", token);
				try {
				
				//1. 검증
					Claims claims = jwtTokenManager.tokenValidation(token);
				//2. token 정보 추출
				//3. Authentication을 SecurityContextHolder에 넣기
					Authentication authentication = jwtTokenManager.getAuthentication(token);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				//4. accessToken을 생성 
					String accessToken= jwtTokenManager.createAccessToken(authentication);
				//5. response의 header에 accessToken을 넣기 
					response.setHeader("AccessToken", accessToken);
				}catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
				
			}
		}
		chain.doFilter(request, response);
	}
	
	
	
}
