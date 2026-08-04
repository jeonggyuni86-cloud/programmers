package com.example.oauth2_basic_board.service;


import com.example.oauth2_basic_board.config.security.CustomUserDetails;
import com.example.oauth2_basic_board.domain.entity.Member;
import com.example.oauth2_basic_board.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member user = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

        return CustomUserDetails.builder()
                .user(user)
                .build();
    }
}
