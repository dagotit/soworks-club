package com.gmail.dlwk0807.dagachi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.dlwk0807.dagachi.dto.member.MemberAuthRequestDTO;
import com.gmail.dlwk0807.dagachi.dto.member.MemberLoginRequestDTO;
import com.gmail.dlwk0807.dagachi.dto.member.MemberResponseDTO;
import com.gmail.dlwk0807.dagachi.dto.token.TokenDTO;
import com.gmail.dlwk0807.dagachi.entity.Authority;
import com.gmail.dlwk0807.dagachi.entity.Company;
import com.gmail.dlwk0807.dagachi.entity.Member;
import com.gmail.dlwk0807.dagachi.service.AuthService;
import com.gmail.dlwk0807.dagachi.vo.ApiMessageVO;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_MSG;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSignup() throws Exception {
        MemberAuthRequestDTO requestDTO = new MemberAuthRequestDTO("dlwk0807@naver.com","1234","페럼","5층", "12345678910","user1", "softworks", "20201212", "별명", "19961212", "Y","ROLE_ADMIN");
        Company company = Company.builder()
                .bizNo("12345678910")
                .addressDtl("5층")
                .companyDate("20201212")
                .companyName("softworks")
                .address("페럼")
                .build();
        // Set your requestDTO properties here
        Member member = requestDTO.toMember(passwordEncoder, company);

        // Set your expected responseVO properties here
        MemberResponseDTO res = MemberResponseDTO.of(member);

        when(authService.signup(any(MemberAuthRequestDTO.class))).thenReturn(res);

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respMsg").value(OK_RESP_MSG))
                .andExpect(jsonPath("$.respBody").exists())
                .andExpect(jsonPath("$.respCode").value(OK_RESP_CODE));
    }

}