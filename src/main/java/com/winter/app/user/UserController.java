package com.winter.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user/*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("join")
	public void join()throws Exception{}
	
	@PostMapping("join")
	public String join(UserVO userVO, @RequestParam("avatar") MultipartFile avatar)throws Exception{
		userService.join(userVO, avatar);
		return "redirect:/";
	}

}
