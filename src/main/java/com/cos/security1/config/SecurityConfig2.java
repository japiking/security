package com.cos.security1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.security1.config.oauth.PrincipalOauth2UserService;
import com.cos.security1.filter.BasicFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig2 {
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	@Autowired
	BasicFilter basicFilter;
	
	//해당 메소드의 리턴되는 오브젝트를 loc로 등록
	@Bean
	BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()		// 요청에 의한 보안검사 시작
		.antMatchers("/user/**").authenticated() //로그인 되며-인증되면 무조건 호출 가능
		.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") //접근시 롤체크
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') ")
		.anyRequest().permitAll()		// 나머지 API 는 접근가능
		.and()
		.formLogin()				//권한 없을경우 로그인 페이지 이동처리
		.loginPage("/loginForm")	//로그인페이지 주소
		.usernameParameter("username2") //Username파라미터가 다를경우
		.passwordParameter("password") //Username파라미터가 다를경우
		.loginProcessingUrl("/login")//login주소가 호출이 되면 시큐리티가 낚아채서 대신로그인을 진행
		.defaultSuccessUrl("/")
		.and()						//Oauth2 구글 로그인
		.oauth2Login()
		.loginPage("/loginForm")	//구글 로그인이 완료된 뒤의 후처리가 필요함. Tip. 코드X,(엑세스토큰+사용자프로필정보 O)
		.userInfoEndpoint()			//1.코드받기(인증), 2엑세스토큰(권한), 3.사용자프리필 정보를 가져와서 4.그 정보를 토대로 회원가입을 자동으로 진행시키기도 함.
		.userService(principalOauth2UserService);  //4-2. (이메일, 전화번호, 이름, 아이디) ->(집주소), 백화점몰 ->(vip등급, 일반등급) 그 정보가 부족할 경우
//		.anyRequest().permitAll();
		http.addFilterBefore(basicFilter,  BasicFilter.class);
		
		return http.build();
	}
	
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
}
