package com.gmail.dlwk0807.dagotit.controller;

import com.gmail.dlwk0807.dagotit.dto.MemberRequestDto;
import com.gmail.dlwk0807.dagotit.dto.TokenDto;
import com.gmail.dlwk0807.dagotit.service.AuthService;
import com.gmail.dlwk0807.dagotit.vo.ApiMessageVO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ApiMessageVO signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(authService.signup(memberRequestDto))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @PostMapping("/login")
    public ApiMessageVO login(@RequestBody MemberRequestDto memberRequestDto, HttpServletResponse response) {

        TokenDto token = authService.login(memberRequestDto);
        setHeaderCookie(response, token, 7 * 24 * 60 * 60);

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(token)
                .respCode(OK_RESP_CODE)
                .build();
    }

    @GetMapping("/reissue")
    public ApiMessageVO reissue(@CookieValue(value = "refreshToken") Cookie cookie, HttpServletResponse response) {
        String refreshToken = cookie.getValue();
        TokenDto reissue = authService.reissue(refreshToken);

        setHeaderCookie(response, reissue, 7 * 24 * 60 * 60);

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(reissue)
                .respCode(OK_RESP_CODE)
                .build();
    }

    private static void setHeaderCookie(HttpServletResponse response, TokenDto reissue, long maxAge) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", reissue.getRefreshToken())
                .maxAge(maxAge)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
        response.setHeader("Set-Cookie", cookie.toString());
    }

    @GetMapping("/logout")
    public ApiMessageVO logout(@CookieValue(value = "refreshToken") Cookie cookie, HttpServletResponse response) {
        String refreshToken = cookie.getValue();
        TokenDto reissue = authService.reissue(refreshToken);

        authService.logout(cookie.getValue());

        setHeaderCookie(response, reissue, 0);

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody("ok")
                .respCode(OK_RESP_CODE)
                .build();
    }
}

