package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.config.exception.CustomException;
import com.cos.security1.entity.TestVO;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;


@Controller //View 리턴
public class IndexController {
	
	@Value("${spring.profiles.active}")
	private String activeProfile;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder  bCryptPasswordEncoder;
	
	@GetMapping("/test/login")
	public @ResponseBody String loginTest(Authentication authentication, 
			@AuthenticationPrincipal PrincipalDetails userDetails) {//DI(의존성 주입)
		
		System.out.println("/test/login =====================");
		PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
		System.out.println("authentication : " + principalDetails.getUser());
		
		System.out.println("userDetails : " + userDetails.getUser());
		
		return "세션 정보 확인하기";
	}
	
	@GetMapping("/test/oauth/login") 
	public @ResponseBody String testOauthLogin(Authentication authentication
			, @AuthenticationPrincipal OAuth2User oauth) {//DI(의존성 주입)
		
		System.out.println("/test/oauth/login =====================");
		OAuth2User oauth2User = (OAuth2User)authentication.getPrincipal();
		System.out.println("authentication : " + oauth2User.getAttributes());
		
		System.out.println("oauth2User : " + oauth.getAttributes());
		
		return "Oauth 세션 정보 확인하기";
	}
	
	//localhost:8080/
	//localhost:8080
	@GetMapping({"","/"})
	public String index() {
		
		//머스태치 기본폴더 src/main/resources/
		//뷰리졸버 설정 : templates(prefix), .mustache(suffix) 생략가능
		return "index"; //src/main/resource/templates/index.mustache
	}
	
	//Oauth 로그인을 해도 PrincipalDetails가 받음
	//일반 로그인을 하더라도 PrincipalDetails가 받음
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("principalDetails : " + principalDetails.getUser());
		return "user";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manger() {
		return "manger";
	}
	
	//스프링 시큐리티 해당주소를 낚아채버리네요 - SecurityConfig 파일 생성 후 작동 안함.
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() { 
		return "joinForm";
	}
	 
	@PostMapping("/join")
	public String join(@ModelAttribute("User") User user) {
		System.out.println(user);
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		
		userRepository.save(user);//회원강비 잘됨, 비밀번호 1234 -> 시큐리티로 로그인 할 수 없음. 이유는 패스워드가 암호화가 안되었기 때문
		return "redirect:/loginForm";
	}
	
	@Secured("ROLE_ADMIN") //특정 메서드만 권한 넣고 싶을때
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")//2개이상 걸고 싶을경우
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "데이터정보";
	}
	
	@PostMapping("/test11")
	public @ResponseBody TestVO test11(@ModelAttribute TestVO testVO ) {
		
		TestVO aa = new TestVO();
		aa.setApiRspsCd("0000"); 
		aa.setApiRspsMsg("정상");
		
		System.out.println("req : " + testVO.getApiRspsCd());
		
		System.out.println("profile : " + System.getProperty("spring.profiles.active"));
		System.out.println("profile2 : " + activeProfile);
		
		if("11".equals("11")) {
			throw new CustomException("444", "user exception");
		}
		
		return aa;
	}
}
