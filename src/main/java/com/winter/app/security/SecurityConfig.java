package com.winter.app.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.winter.app.user.UserService;
import com.winter.app.user.UserSocialService;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity//(debug=true)
public class SecurityConfig {
	
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserSocialService userSocialService;
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
					.cors(cors-> cors.configurationSource(this.configurationSource())) 
					.csrf(csfr-> csfr.disable())
					
					/*권한 적용 순서주의*/
					.authorizeHttpRequests(authorizeRequest->{
						authorizeRequest
						//.requestMatchers("/notices").hasRole("ADMIN")
						
						.requestMatchers("/notices").authenticated() //로그인한사람만 연결하겠다
						//.requestMatchers("/notice/add", "/notice/update", "/notice/delete").hasRole("ADMIN")
						//.requestMatchers("/user/mypage","/user/update","/user/logout").authenticated()
						//.requestMatchers("/manager/**").hasAnyRole("ADMIN","MEMBER")
						.anyRequest().permitAll()
						;
						
					})
		
					/*Form 관련 설정*/
					.formLogin(formlogin -> {
						formlogin.disable()
							
						;
					})
				
					
					//동시접속 방지
					.sessionManagement(s->{
						s.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
						
						;
					})
					.httpBasic(httpBasic-> httpBasic.disable())
					
					
		
		  /*.oauth2Login(oauth2Login->{ 
			 oauth2Login 
			  .userInfoEndpoint(user->{
		      user.userService(userSocialService); 
		      });
			   
			  })*/
		  
		  .addFilter(new JwtLoginFilter(authenticationConfiguration.getAuthenticationManager(),jwtTokenManager))
		  .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(),jwtTokenManager))
					
		;		
					
		return httpSecurity.build();
	}
	
	CorsConfigurationSource configurationSource() {
		CorsConfiguration corsconfiguration = new CorsConfiguration();
		
		//GET 메서드 허용 (그 외 메서드는 허용 안함)
		corsconfiguration.setAllowedOrigins(List.of("*"));
		//그 외 메서드들도 허용할 수 있게 추가 
		corsconfiguration.setAllowedMethods(List.of("POST","DELETE","PATCH","PUT", "GET"));
		
		corsconfiguration.setAllowedHeaders(List.of("*"));
		//응답으로 나갈 때 허용해줌 
		corsconfiguration.setExposedHeaders(List.of("AccessToken","RefreshToken"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsconfiguration);
		return source;
		
		};
	}
	
	
	
	
	
	
	
	
	
	


