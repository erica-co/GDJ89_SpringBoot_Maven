package com.winter.app.user;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.Range;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVO implements UserDetails, OAuth2User {
	
	@NotBlank(message = "ID는 필수", groups = JoinGroup.class)
	private String username;
	@Size(min = 8, max = 16)
	@NotBlank(groups = JoinGroup.class)
	private String password;
	private String passwordCheck;
	
	@NotBlank(groups = {UpdateGroup.class, JoinGroup.class})
	private String name;
	@NotBlank(groups = {UpdateGroup.class, JoinGroup.class})
	private String phone;
	//@Range(min = 0, max = 150)
	//private int age;
	@Email(groups = {UpdateGroup.class, JoinGroup.class})
	private String email;
	@Past(groups = {UpdateGroup.class, JoinGroup.class})
	private Date birth;
	private String fileName;
	private String oriName;
	
	private boolean status;
	
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	//아이디는 맞고 비번이 틀린 경우
	//BadCredentialsException: 자격 증명에 실패하였습니다.
	
	private List<RoleVO> list;
	
	//** oauth2user **//
	private Map<String, Object> attributes;
	
	private String accessToken;
	private String sns;
	
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// ROLE_NAME을 리턴
		List<GrantedAuthority> ar = new ArrayList<>();
		
		for(RoleVO roleVO:this.list) {
			GrantedAuthority g= new SimpleGrantedAuthority(roleVO.getRoleName());
			ar.add(g);
		}
			
		return ar;
	}
	
	

	/*
	 * @Override 
	 * public boolean isAccountNonExpired() 
	 * { // TODO Auto-generated method stub
	 * 
	 * //false-> return true; }
	 * 
	 * @Override 
	 * public boolean isAccountNonLocked() { // TODO Auto-generated method
	 * stub
	 * 
	 * //false-> LockedException:사용자 계정이 잠겨있습니다 return true; }
	 * 
	 * @Override public boolean isCredentialsNonExpired() { // TODO Auto-generated
	 * method stub
	 * 
	 * //false-> CredentialsExpiredException : 자격 증명 유효 기간이 만료되었습니다 return true; }
	 * 
	 * @Override public boolean isEnabled() { // TODO Auto-generated method stub
	 * 
	 * //false-> DisabledException:유효하지 않은 사용자입니다. return true; }
	 */
	
	
	
	

}
