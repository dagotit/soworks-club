package com.gmail.dlwk0807.dagachi.service;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = {"prod"})
class MailSendServiceTest {

    @Autowired
    JavaMailSender mailSender;

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

    @Test
    @DisplayName("mailSend빈주입")
    void mailSend빈주입() throws Exception {
        //given
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        System.out.println("mimeMessage = " + mimeMessage);

        //when

        //then
    }
}