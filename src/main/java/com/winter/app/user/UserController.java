package com.winter.app.user;

import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user/*")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("mypage")
	public void mypage() {}
	
	@GetMapping("update")
	public void update(@ModelAttribute UserVO userVO, HttpSession session)throws Exception{
		Enumeration<String> e = session.getAttributeNames();
		while(e.hasMoreElements()) {
			System.out.println(e.nextElement());
		}
		Object obj = session.getAttribute("SPRING_SECURITY_CONTEXT");
		System.out.println(obj.getClass());
		
		SecurityContextImpl impl = (SecurityContextImpl)obj;
		
		Authentication authentication= impl.getAuthentication();
		log.info("authentication: {}", authentication);
		
	}
	
	@PostMapping("update")
	public void update(@Validated(UpdateGroup.class) UserVO userVO, BindingResult bindingResult)throws Exception{}
	
	@GetMapping("join")
	public void join(@ModelAttribute UserVO userVO)throws Exception{}
	
	@PostMapping("join")
	public String join(@Validated(JoinGroup.class) UserVO userVO,BindingResult bindingResult,  @RequestParam("avatar") MultipartFile avatar)throws Exception{
		log.info("{}", userVO);
		
		log.info("Count : {} ", bindingResult.getErrorCount());
		
		if(userService.userErrorCheck(userVO, bindingResult)) {
			return "user/join";
		}
		
		userService.join(userVO, avatar);
		return "redirect:/";
	}

	
	@GetMapping("login")
	public void login()throws Exception{}
	
	/* filter에서 걸러져서 앞으로는 필요없음
	 * @PostMapping("login") public String login(UserVO userVO, HttpSession
	 * session)throws Exception{
	 * 
	 * userVO =userService.detail(userVO); if(userVO != null) {
	 * session.setAttribute("user", userVO); }
	 * 
	 * return "redirect:/"; }
	 */
	
	/* securityConfig에서 설정해놔서 controller 필요없음
	 * @GetMapping("logout") public String login(HttpSession session)throws
	 * Exception{ session.invalidate(); return "redirect:/"; }
	 */
	
}
