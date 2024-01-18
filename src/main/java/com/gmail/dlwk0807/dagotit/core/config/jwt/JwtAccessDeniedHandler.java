package com.gmail.dlwk0807.dagotit.core.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.dlwk0807.dagotit.vo.ApiMessageVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // response에 데이터 넣기
        ApiMessageVO apiMessageVO = ApiMessageVO.builder()
                .respCode("BIZ_014")
                .respBody("")
                .respMsg("변경 권한이 존재하지 않습니다.").build();

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiMessageVO));
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    }
}
