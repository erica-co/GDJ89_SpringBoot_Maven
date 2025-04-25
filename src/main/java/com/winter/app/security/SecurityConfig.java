package com.winter.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import com.winter.app.user.UserService;
import com.winter.app.user.UserSocialService;

@Configuration
@EnableWebSecurity//(debug=true)
public class SecurityConfig {
	
	@Autowired
	private SecurityLoginSuccessHandler loginSuccessHandler;
	@Autowired
	private SecurityLoginFailHandler loginFailHandler;
	@Autowired
	private UserService userService;
	@Autowired
	private UserSocialService userSocialService;
	@Autowired
	private SecurityLogoutHandler securityLogoutHandler;
	@Autowired
	private SecurityLogoutSuccessHandler logoutSuccessHandler;
	
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
						//.defaultSuccessUrl("/")/*로그인 성공했을 때 가게될 다음 경로*/
						.successHandler(loginSuccessHandler)
						
						//.failureForwardUrl("/user/login")/*로그인 실패했을 때*/
						.failureHandler(loginFailHandler)
						.permitAll()
						;
					})
					
					/*logout 관련 설정*/
					.logout(logout->{
						logout
						.logoutUrl("/user/logout")
						//.logoutSuccessUrl("/")
						.addLogoutHandler(securityLogoutHandler) /*실행순서 1번*/
						//.logoutSuccessHandler(logoutSuccessHandler)/*addLogoutHandler가 성공하면 실행됨 2번*/
						.invalidateHttpSession(true) /*session 소멸*/
						.permitAll()
						;
					})
					
					.rememberMe(rememberme->{
						rememberme
						.rememberMeParameter("remember-me")
						.tokenValiditySeconds(60)
						.key("rememberkey")
						.userDetailsService(userService)
						.authenticationSuccessHandler(loginSuccessHandler)
						.useSecureCookie(false)
						;
					})
					
					//동시접속 방지
					.sessionManagement(s->{
						s
						.sessionFixation().none()//세션보호를 하지않겠다
						//.newSession()//.changeSessionId()
						.invalidSessionUrl("/")
						.maximumSessions(1)
						.maxSessionsPreventsLogin(true)//false:이전사용자 자동로그아웃
						.expiredUrl("/")
						;
					})
		
		  .oauth2Login(oauth2Login->{ 
			  oauth2Login 
			  .userInfoEndpoint(user->{
		      user.userService(userSocialService); })
			  ; 
			  })
		 
					
					
					
					
					
					
					
					;
					
					
					
					
		return httpSecurity.build();
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
