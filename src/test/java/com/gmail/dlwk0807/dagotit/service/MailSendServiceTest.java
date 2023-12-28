package com.gmail.dlwk0807.dagotit.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class MailSendServiceTest {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("passwordEncodeTest")
    void passwordEncodeTest() throws Exception {
        //given
        String test1 = passwordEncoder.encode("eK03a0mR");
        String test2 = passwordEncoder.encode("1234");
        //when
        System.out.println("test1 = " + test1);
        System.out.println("test2 = " + test2);

        //$2a$10$rA29yCuav/dRkywlKHCtAuJZaXGtcqHipSeVjHCh6BF9DooH.b.EG
        //then
    }
}