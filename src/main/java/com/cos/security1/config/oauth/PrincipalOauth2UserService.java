package com.cos.security1.config.oauth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	//구글 로그인 후 후처리되는 함수 : 구글로부터 받은 userRequest 데이터에 대한 후처리
	// 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
		System.out.println("userRequest : " + userRequest.getClientRegistration());//registrationId로 어떤 Oauth로 로그인 했는지 확인 가능
		System.out.println("AccessToken : " + userRequest.getAccessToken().getTokenValue());
		System.out.println("ClientName : " + userRequest.getClientRegistration().getClientName());
		System.out.println("ClientId : " + userRequest.getClientRegistration().getClientId());
		
		OAuth2User oauth2User =  super.loadUser(userRequest);
		
		
		//구그로그인 버튼 클릭 -> 구글로그인 창 -> 로그인 완료 -> code를 리턴(Oauth2-Client라이브러리) -> Access Token 요청
		//useRequest 정보 -> 회원프로필 받아야함(이때 받는 함수가 loadUser) -> 구글로부터 회원프로필 받아준다.
		System.out.println("Attributes : " +oauth2User.getAttributes());
		
		OAuth2User oAuth2UserInfo = null;
		
		
		String provider = userRequest.getClientRegistration().getClientId();
		String providerId = oauth2User.getAttribute("sub");
		String email = oauth2User.getAttribute("email");
		String password = "겟인데어";
		String username = provider+"_"+providerId;//goolge_1097428561829427686
		String role = "ROLE_USER";
		if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			Map map = (Map)oauth2User.getAttribute("response");
			provider = userRequest.getClientRegistration().getClientId();
			providerId = (String)map.get("id");
			email = (String)map.get("email");
			password = "겟인데어";
			username = (String)map.get("name"); 
			role = "ROLE_USER";
		}else {
			provider = userRequest.getClientRegistration().getClientId();
			providerId = oauth2User.getAttribute("sub");
			email = oauth2User.getAttribute("email");
			password = "겟인데어";
			username = provider+"_"+providerId;//goolge_1097428561829427686
			role = "ROLE_USER";
		}
		System.out.println("username : " +username);
		//DB에서 사용자 조회
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			System.out.println("구글 로그인이 최초입니다.");
			userEntity = new User(username, password, email, role, provider, providerId, null);
			userRepository.save(userEntity);
		}else {
			System.out.println("구글 로그인이을 이미 한적이 있습니다.");
		}
		//회원가입을 강제로 진행
		return new PrincipalDetails(userEntity, oauth2User.getAttributes());
		
		//return oauth2User;
	}
}
