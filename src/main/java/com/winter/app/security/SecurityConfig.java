package com.winter.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity//(debug=true)
public class SecurityConfig {
	
	//정적자원들을 security에서 제외
	@Bean
	WebSecurityCustomizer customizer() {
		//WebSecurityCustomizer s = ()->{}
		//return s;
		return (web)->{
			web.ignoring()
				.requestMatchers("/css/**")
				.requestMatchers("/images/**", "/img/**")
				.requestMatchers("/js/**","/vendor/**")
				;
		};
	}
	
	//인증과 권한에 관한 설정
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		
		httpSecurity
					/* cors 허용, filter에서 사용 가능*/
					/*다른 서버에서 오는 요청 허용: cors*/
					.cors(cors-> cors.disable()) 
					.csrf(csfr-> csfr.disable())
					
					/*권한 적용 순서주의*/
					.authorizeHttpRequests(authorizeRequest->{
						authorizeRequest
						.requestMatchers("/notice/add", "/notice/update", "/notice/delete").hasRole("ADMIN")
						.requestMatchers("/user/mypage","/user/update","/user/logout").authenticated()
						.requestMatchers("/manager/**").hasAnyRole("ADMIN","MEMBER")
						.anyRequest().permitAll()
						;
						
					})
		
					/*Form 관련 설정*/
					.formLogin(formlogin -> {
						formlogin
						.loginPage("/user/login")/*우리가 만든 로그인창으로 가겠다*/
							//.usernameParameter("id")
							//.passwordParameter("pw")
						.defaultSuccessUrl("/")/*로그인 성공했을 때 가게될 다음 경로*/
						.failureForwardUrl("/user/login")/*로그인 실패했을 때*/
						.permitAll()
						;
					})
					
					/*logout 관련 설정*/
					.logout(logout->{
						logout
						.logoutUrl("/user/logout")
						.logoutSuccessUrl("/")
						.invalidateHttpSession(true) /*session 소멸*/
						.permitAll()
						;
					})
					
					;
					
					
					
		return httpSecurity.build();
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
