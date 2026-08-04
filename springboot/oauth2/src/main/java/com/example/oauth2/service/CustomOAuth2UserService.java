package com.example.oauth2.service;

import com.example.oauth2.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

// * CustomOAuth2UserService -> OAuth2 파이프라인에서 개발자가 구현하는 첫 번쨰 훅(hook)
// 코드-토큰 교환 -> 사용자 정보 조회 -> loadUser() 호출 -> Authentication 생성 -> SuccessHandler 호출

// [명시적 가입 정책] : SNS 인증을 통과했다고 곧바로 가입시키지 않는다.
// - 기존 회원(provider + providerID 일치): 프로필만 최신값으로 갱신하고 로그인 진행
// - 미가입: DB에 저장하지 않고 미가입 principal(CustomOAuth2User.unregistered)을 반환
// -> OAuth2UserSuccessHandler가 이를 받아 가입 안내로 분기하고,
// 사용자가 동의해야 그때 비로소 계정이 생성된다.
// (이메일이 같으면 제공자가 다르면 별개 계정 -> 식별 기준 : provider + providerId)

// * DefaultOAuth2UserService
// 부모의 loadUser()가 "제공자 user-info 엔드 포인트 HTTP요청을 보내 프로필을 받아오는"
// 코드 전부를 이미 갖고 있다. 통신을 다시 구현할 이유가 없으므로,
// super.loadUser()로 결과만 받고, 그 뒤의 "우리 도메인 연결"만 덧붙인다.
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        return super.loadUser(userRequest);
    }


}
