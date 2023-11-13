package com.gmail.dlwk0807.dagotit.controller;

import com.gmail.dlwk0807.dagotit.controller.dto.MemberRequestDto;
import com.gmail.dlwk0807.dagotit.controller.dto.MemberResponseDto;
import com.gmail.dlwk0807.dagotit.controller.dto.TokenDto;
import com.gmail.dlwk0807.dagotit.controller.dto.TokenRequestDto;
import com.gmail.dlwk0807.dagotit.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto, HttpServletResponse response) {

        TokenDto token = authService.login(memberRequestDto);

        extracted(response, token);

        return ResponseEntity.ok(token);
    }

    @GetMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@CookieValue(value = "refreshToken") Cookie cookie, HttpServletResponse response) {
        String refreshToken = cookie.getValue();
        TokenDto reissue = authService.reissue(refreshToken);

        extracted(response, reissue);

        return ResponseEntity.ok(reissue);
    }

    private static void extracted(HttpServletResponse response, TokenDto reissue) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", reissue.getRefreshToken())
                .maxAge(7 * 24 * 60 * 60)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
        response.setHeader("Set-Cookie", cookie.toString());
    }

}

