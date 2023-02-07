package com.cos.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.security1.model.User;

import lombok.Data;

//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
//로그인 진행이 완료가되면 session을 만들어줍니다.(Security ContextHolder)
//오브젝트 -> Authentication타입 객체
//Authenticaion안에 User정보가 있어야 됨.
//User오브젝트타입 -> UserDetails 타입 객체

//Security Session -> Authentication -> UserDetails(PrincipalDetails)
//Security Session에는 Authentication타입으로만 저장 가능
//Authentication 객체안에 User정보 저장시 UserDetails타입으로 저장 가능
//아래는 UserDetails을 상속받아 PrincipalDetails 생성

@Data
public class PrincipalDetails implements UserDetails, OAuth2User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2435918478451322374L;
	private User user;//콤포지션
	private Map<String,Object> attributes;
	
	//일반 로그인
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	//Oauth2 로그인
	public PrincipalDetails(User user, Map<String,Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	
	
	
	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	//해당 User의 권한을 리턴하는 곳!!
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		
		return collect;
	}

	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	//계정 만료여부 : 필요에 따라 로직 추가
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정 잠김여부 : 필요에 따라 로직 추가
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//계정 1년이 지나지 않았니??
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		//우리 사이트!! 1년동안 회원이 로그인을 안하면!! 휴면 계정으로 하기로 함.
		//user.getLoginDate();
		//현재시간 - 로그인시간 -> 1년을 초과하면 return false;
		return true;
	}



	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}



	@Override
	public String getName() {
		return null;
	}

}
