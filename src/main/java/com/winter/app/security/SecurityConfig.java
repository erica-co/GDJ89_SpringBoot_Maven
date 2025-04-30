package com.winter.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.winter.app.security.jwt.JwtAuthenticationFilter;
import com.winter.app.security.jwt.JwtLoginFilter;
import com.winter.app.security.jwt.JwtTokenManager;
import com.winter.app.user.UserService;
import com.winter.app.user.UserSocialService;

@Configuration
@EnableWebSecurity//(debug=true)
public class SecurityConfig {
	
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	@Autowired
	private JwtTokenManager jwtTokenManager;
	
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
					.formLogin(formlogin -> formlogin.disable())
					
					
					
					/*session 인증 방식이 아닌 
					 * Token 기반 인증 방식을 사용하기 때문에
					 * session 사용 안함
					 * 클라이언트에서 Token값을 서버에 전달하지 않더라도 로그인 됨 
					 * */
					.sessionManagement(session ->{
						//세션을 사용하지 않겠다, session 안만듦 (stateless: 무상태) 
						//토큰을 session에 저장하지 않음 
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
					})
					
					/* cookie와 session을 이용하는 방식이 아니라
					 * request Header에 ID, PW를 직접 보내는 방식이라서 보안에 취약
					 * HTTP basic방식은 해제
					 * **/
					.httpBasic(HttpBasic -> HttpBasic.disable())
				
					
					//JwtLoginFilter에서 component 안해서 여기서 직접 객체 만들기
					.addFilter(new JwtLoginFilter(authenticationConfiguration.getAuthenticationManager(), jwtTokenManager))
					.addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(),jwtTokenManager))
					
					;
					
					
					
					
		return httpSecurity.build();
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
