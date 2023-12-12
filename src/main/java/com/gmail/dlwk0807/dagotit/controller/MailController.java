package com.gmail.dlwk0807.dagotit.controller;

import com.gmail.dlwk0807.dagotit.dto.EmailCertificationRequest;
import com.gmail.dlwk0807.dagotit.service.MailSendService;
import com.gmail.dlwk0807.dagotit.service.MailVerifyService;
import com.gmail.dlwk0807.dagotit.vo.ApiMessageVO;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mails")
public class MailController {

    private final MailSendService mailSendService;
    private final MailVerifyService mailVerifyService;

    @PostMapping("/send-certification")
    public ApiMessageVO sendCertificationNumber(@Validated @RequestBody EmailCertificationRequest request)
            throws MessagingException, NoSuchAlgorithmException {
        mailSendService.sendEmailForCertification(request.getEmail());

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody("")
                .respCode(OK_RESP_CODE)
                .build();
    }

    @GetMapping("/verify")
    public ApiMessageVO verifyCertificationNumber(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "certificationNumber") String certificationNumber
    ) {
        mailVerifyService.verifyEmail(email, certificationNumber);

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody("")
                .respCode(OK_RESP_CODE)
                .build();
    }
}