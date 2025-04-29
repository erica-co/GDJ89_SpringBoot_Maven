package com.winter.app.websocket;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoginUsers {
	
	//static으로 객체 안만들고 사용가능
	//final의 규칙: 변수이름 대문자
	//set은 중복 불가능
	public static final Set<String> USERNAMES = new HashSet<>(); 
	
	// 생성자 
	// 인스턴스 초기화 블럭 {}
	/*
	 * static { LoginUsers.USERNAMES = new ArrayList<>() }
	 */
}
