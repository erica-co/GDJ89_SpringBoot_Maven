package com.winter.app.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.winter.app.user.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecurityLogoutHandler implements LogoutHandler{
		
		@Override
		public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
			log.info("Auth : {}", authentication);
			
			//social로그인일 경우 logout 요청 진행
			//authentication(참조변수)
			if(authentication instanceof OAuth2AuthenticationToken) {
				UserVO userVO=(UserVO)authentication.getPrincipal();
				
				if(userVO.getSns().toUpperCase().equals("KAKAO")) {
					
				}
				//log.info("social 사용자 : {}", userVO.getSns());
				
				
			}
			
		}
}
