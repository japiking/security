//package com.cos.security1.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@SuppressWarnings("deprecation")
//@Configuration
//@EnableWebSecurity //활성화시 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)//secured 어노테이션 활성화, preAuthorize, postAuthorize 어노테이션 활성화
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//	
//	//해당 메소드의 리턴되는 오브젝트를 loc로 등록
//	@Bean
//	BCryptPasswordEncoder encodePwd() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable();			//토큰체크 비활성화
//		http.authorizeRequests()		// 요청에 의한 보안검사 시작
//		//.anyRequest().authenticated() //어떤 요청에도 보안검사를 한다.
//		.antMatchers("/user/**").authenticated() //로그인 되며-인증되면 무조건 호출 가능
//		.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") //접근시 롤체크
//		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') ")
//		.anyRequest().permitAll()
//		.and()
//		.formLogin()				//권한 없을경우 로그인 페이지 이동처리
//		.loginPage("/loginForm")	//로그인페이지 주소
//		.usernameParameter("username2") //Username파라미터가 다를경우
//		.passwordParameter("password")//패스워드 파라미터명 설정
//		.loginProcessingUrl("/login")//login주소가 호출이 되면 시큐리티가 낚아채서 대신로그인을 진행
//		.defaultSuccessUrl("/");		//로그인 성공 후 이동 페이지('/'는 사용자가 접근하려던 페이지
//		.failureUrl("/loginForm")// 로그인 실패 후 이동 페이지
//		.successHandler(loginSuccessHandler())//로그인 성공 후 핸들러 (해당 핸들러를 생성하여 핸들링 해준다.)
//      .failureHandler(loginFailureHandler());//로그인 실패 후 핸들러 (해당 핸들러를 생성하여 핸들링 해준다.)

/*
		.successHandler(new AuthenticationSuccessHandler() {
		    @Override
		    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
																						HttpServletResponse response, 
																						Authentication authentication) throws IOException, ServletException {
		        System.out.println("authentication:: "+ authentication.getName());
		        response.sendRedirect("/");
		    }
		})//로그인 성공 후 핸들러
		.failureHandler(new AuthenticationFailureHandler() {
		    @Override
		    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
																						HttpServletResponse response,
																						AuthenticationException e) throws IOException, ServletException {
		        System.out.println("exception:: "+e.getMessage());
		        response.sendRedirect("/login");
		    }
		})//로그인 실패 후 핸들러
		
		.sessionManagement() //세션 관리 기능이 작동함
        .invalidSessionUrl("/invalid")//세션이 유효하지 않을 때 이동 할 페이지
        .maximumSessions(1)//최대 허용 가능 세션 수, (-1: 무제한)
        .maxSessionsPreventsLogin(true)//동시 로그인 차단함, false: 기존 세션 만료(default)
        .expiredUrl("/expired");//세션이 만료된 경우 이동할 페이지
        .sessionFixation().changeSessionId();// 기본값 -> 세션은 유지하되 세션아이디는 계속 새로 발급(servlet 3.1이상 기본 값)
                                                     // none, migrateSession, newSession
*/
//	}
//}
