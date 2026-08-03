package com.example.oauth2.config.oauth2;

import com.example.oauth2.domain.entity.entitiy.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

// * CustomOAuth2User
// CustomUserDetails 의 OAuth2 버전이다
// - 자체 로그인 경로 : 시큐리티가 요구하는 표준 - UserDetails -> CustomUserDetails가 User를 감싼다
// - 소셜 로그인 경로 : 시큐리티가 요구하는 표준 - OAuth2User -> 이 클래스가 User를 감싼다
// 즉, 이 클래스도 "우리 도메인(User)과 시큐리티 사이의 어댑터" 이고,
// 어떤 경로로 로그인 하든 Principal에서 우리 User 엔티티로 꺼낼 수 있게 만드는 장치이다.

@Getter
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    // 우리 DB회원 - SuccessHandler가 JWT 발급에 사용
    private final User user;
    //어떤 SNS로 인증했는지
    private final AuthProvider authProvider;
    // 제공자 프로필의 공통 창구(다형성), 제공자별 파싱 결과가 필요할 때 사용
    private final OAuth2UserInfo userInfo;

    // 제공자가 준 원시 속성 맵
    // OAuth2User 계약상 보관 의무가 있고,
    // UserInfo가 안 꺼내주는 필드가 나중에 필요해질 때의 탈출구이기도 하다.
    private final Map<String, Object> attributes;

    // 원시 맵에서 "사용자 식별자"를 가리키는 것. 제공자마다 다르다
    // applcation.yaml provider의 user-name-attribute 값이 여기까지 흘러 들어온다.
    private final String nameAttributeKey;

    // 우리 DB에 이미 가입된 회원인지, SuccessHandler가 "로그인 완료 vs 가입안내" 분기에 사용된다.
    public boolean isRegistered() {
        return user != null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 인간 판단의 재료
    // 빈 리스트를 반환하면, "로그인은 됐지만 아무 권한 없는 사용자"가 되어
    // hasRole 검사를 전부 통과하지 못하므로 반드시 채워야 한다.
    // 미가입(user == null) 상태는 아직 우리 회원이 아니므로 임시권한 "ROLE_GUEST"만 가진다.
    // - 가입 안내 페이지까지만 갈 수 있고, hasRole("USER")가 걸린 자원에는 접근할 수 없다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(user == null)
            return List.of(new SimpleGrantedAuthority("ROLE_GUEST"));
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    // OAuth2User 계약 : "이 principal의 이름"을 반환한다.
    // UserDetails.getusername()에 대응
    @Override
    public String getName() {
        return String.valueOf(attributes.get(nameAttributeKey));
    }
}
