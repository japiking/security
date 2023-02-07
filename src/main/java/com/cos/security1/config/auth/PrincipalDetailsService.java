package com.cos.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

//Security Session -> Authentication -> UserDetails(PrincipalDetails) 에서 Authentication 부분

// 시큐리티 설정에서 loginProcessingUrl("/login");
// login요청이 오면 자동으로 UserDetailsService타입으로 loC되어 있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	//UserDetails을 상속받아 만든 PrincipalDetails 리턴
	//시큐리티 session (내부 Authentication (내부 UserDetails))
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("username : " + username);
		
		// TODO Auto-generated method stub
		User userEntity = userRepository.findByUsername(username);
		if(userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		return null;
	}

}
