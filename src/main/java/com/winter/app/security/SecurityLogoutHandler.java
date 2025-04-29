package com.winter.app.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.winter.app.user.UserDAO;
import com.winter.app.user.UserVO;
import com.winter.app.websocket.LoginUsers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class SecurityLogoutHandler implements LogoutHandler{
		
		@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
		private String adminKey;
		@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
		private String restKey;
		@Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
		private String redirect;
		
		@Autowired
		private UserDAO userDAO;
	
		@Override
		public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
			log.info("Auth : {}", authentication);
			
			UserVO userVO2 = (UserVO)authentication.getPrincipal();
			userVO2.setStatus(false);
			try {
				userDAO.statusChange(userVO2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			LoginUsers.USERNAMES.remove(authentication.getName());
			
			//social로그인일 경우 logout 요청 진행
			//authentication(참조변수)
			if(authentication instanceof OAuth2AuthenticationToken) {
				UserVO userVO=(UserVO)authentication.getPrincipal();
				
				if(userVO.getSns().toUpperCase().equals("KAKAO")) {
					
				}
				log.info("social 사용자 : {}", userVO.getSns());
				
				
			}
			
		}
		//logout End
		
		private void kakaoLogout(UserVO userVO) {
			//admin key 사용시 parameter 설정
			log.info("admin : {} ",adminKey);
			
			
			Map<String, Object> map = new HashMap<>();
			map.put("target_id_type", "user_id");
			map.put("target_id", userVO.getAttributes().get("id"));
			
			WebClient webClient = WebClient.create();
			Mono<String> res = webClient
				.post()//메서드 형식
				.uri("https://kapi.kakao.com/v1/user/logout")
				//.header("Authorization", "Bearer"+userVO.getAccessToken())
				.header("Authorization", "Bearer"+userVO.getAccessToken())
				.bodyValue(map)
				.retrieve()//내용만 필요하기 때문에 retrieve로 받아도 충분
				.bodyToMono(String.class)
				;
				log.info("Result : {}", res.block());
										
		}
		
		
		
		
		
}
