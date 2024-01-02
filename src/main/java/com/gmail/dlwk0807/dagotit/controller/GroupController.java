package com.gmail.dlwk0807.dagotit.controller;

import com.gmail.dlwk0807.dagotit.dto.EmailCertificationRequest;
import com.gmail.dlwk0807.dagotit.service.MailSendService;
import com.gmail.dlwk0807.dagotit.service.MailVerifyService;
import com.gmail.dlwk0807.dagotit.vo.ApiMessageVO;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final MailSendService mailSendService;
    private final MailVerifyService mailVerifyService;

    @PostMapping("/save")
    public ApiMessageVO sendCertificationNumber(@Validated @RequestBody EmailCertificationRequest request)
            throws MessagingException {
        mailSendService.sendEmailForCertification(request.getEmail(), request.getName());

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

    @GetMapping("/verify-update-password")
    public ApiMessageVO verifyCertificationNumberAndUpdatePassword(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "certificationNumber") String certificationNumber
    ) throws MessagingException {

        mailVerifyService.verifyEmailAndUpdatePassword(email, certificationNumber);

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody("")
                .respCode(OK_RESP_CODE)
                .build();
    }
}