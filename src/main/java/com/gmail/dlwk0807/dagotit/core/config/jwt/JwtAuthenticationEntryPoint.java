package com.gmail.dlwk0807.dagotit.core.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.dlwk0807.dagotit.core.exception.AccessDeniedCustomException;
import com.gmail.dlwk0807.dagotit.vo.ApiMessageVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // response에 데이터 넣기
        ApiMessageVO apiMessageVO = ApiMessageVO.builder()
                .respCode("BIZ_007")
                .respBody("")
                .respMsg("잘못된 로그인 정보입니다.").build();

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiMessageVO));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    }
}
