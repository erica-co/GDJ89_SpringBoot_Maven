package com.winter.app.security;

import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.winter.app.user.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenManager {
	
	@Value("${jwt.accessToken.validTime}")
	private long accessTokenValidTime;
	
	@Value("${jwt.refreshToken.validTime}")
	private long refreshTokenValidTime;
	
	@Value("${jwt.issuer}")
	private String issuer;
	
	@Value("${jwt.secret.key}")
	private String jwtSecretKey;
	
	private Key key;
	
	@Autowired
	private UserService userService;
	
	//1. Token 생성
		@PostConstruct
		public void init() {
			String sec = Base64.getEncoder().encodeToString(jwtSecretKey.getBytes());
			key = Keys.hmacShaKeyFor(sec.getBytes());
		}
		
		public String createAccessToken(Authentication authentication) {
			return this.createToken(authentication, accessTokenValidTime);
		}
		
		public String createRefreshToken(Authentication authentication) {
			return this.createToken(authentication, refreshTokenValidTime);
		}
		
		private String createToken(Authentication authentication, long validTime) {
			
			return Jwts.builder()
					.setSubject(authentication.getName())   //유저의 ID
					.claim("role", authentication.getAuthorities())
					.setIssuedAt(Calendar.getInstance().getTime())
					.setExpiration(new Date(System.currentTimeMillis()+validTime))
					.setIssuer(issuer)
					.signWith(key)
					.compact();
		}
		
		public Claims tokenValidation(String token) throws Exception{
			return Jwts.parser()
					.setSigningKey(this.key)
					.build()
					.parseClaimsJwt(token)
					.getBody();
			
			
		}
		
		public Authentication getAuthentication(String username) {
			UserDetails userDetails = userService.loadUserByUsername(username);
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
			return authentication;
		}


}
