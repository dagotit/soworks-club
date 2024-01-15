package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.core.config.jwt.TokenProvider;
import com.gmail.dlwk0807.dagotit.core.exception.DuplicationMember;
import com.gmail.dlwk0807.dagotit.dto.member.MemberRequestDTO;
import com.gmail.dlwk0807.dagotit.dto.member.MemberResponseDTO;
import com.gmail.dlwk0807.dagotit.dto.token.TokenDTO;
import com.gmail.dlwk0807.dagotit.entity.Member;
import com.gmail.dlwk0807.dagotit.entity.RefreshToken;
import com.gmail.dlwk0807.dagotit.repository.MemberRepository;
import com.gmail.dlwk0807.dagotit.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MemberResponseDTO signup(MemberRequestDTO memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new DuplicationMember("이미 가입되어 있는 유저입니다");
        }

        Member member = memberRequestDto.toMember(passwordEncoder);

        return MemberResponseDTO.of(memberRepository.save(member));
    }

    @Transactional
    public TokenDTO login(MemberRequestDTO memberRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDTO reissue(String strRefreshToken) {
        // 1. Refresh Token 검증
        this.verifiedRefreshToken(strRefreshToken);

        // 2. Refresh Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(strRefreshToken);

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        log.debug("authentication.getName() : {}", authentication.getName());
        RefreshToken refreshToken = refreshTokenRepository.findById(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(strRefreshToken)) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }

    private void verifiedRefreshToken(String encryptedRefreshToken) {
        if (encryptedRefreshToken == null) {
            throw new RuntimeException("RefreshToken이 유효하지 않습니다.");
        }
    }

    public void logout(String strRefreshToken) {
        this.verifiedRefreshToken(strRefreshToken);
        Authentication authentication = tokenProvider.getAuthentication(strRefreshToken);
        RefreshToken refreshToken = refreshTokenRepository.findById(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        refreshTokenRepository.delete(refreshToken);
    }

}
