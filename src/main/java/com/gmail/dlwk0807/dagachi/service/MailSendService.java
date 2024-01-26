package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagachi.core.exception.DuplicationEmailSenderException;
import com.gmail.dlwk0807.dagachi.core.exception.MemberCheckException;
import com.gmail.dlwk0807.dagachi.dto.email.EmailCertificationResponseDTO;
import com.gmail.dlwk0807.dagachi.entity.Member;
import com.gmail.dlwk0807.dagachi.repository.CertificationNumberRepository;
import com.gmail.dlwk0807.dagachi.repository.MemberRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gmail.dlwk0807.dagachi.util.CommonUtil.createCode;

@Service
@RequiredArgsConstructor
@Transactional
public class MailSendService {

    private final JavaMailSender mailSender;
    private final CertificationNumberRepository certificationNumberRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public EmailCertificationResponseDTO sendEmailForCertification(String email, String name) throws MessagingException {

        /**
         * 이메일 인증 사용 2군데
         * 1. 회원가입 이메일 인증
         *      email만 넘김
         * 2. 비밀번호 변경 이메일 인증
         *      비밀번호 변경 시에는 회원여부 판단하기 위해 email, name 넘김
         * 두가지 경우를 checkNameBeforeUpdatePassword 에서  name null 체크로 구분.
         */
        checkNameBeforeUpdatePassword(email, name);

        if (certificationNumberRepository.hasKey(email)) {
            throw new DuplicationEmailSenderException();
        }

        String certificationNumber = createCode();
        String sendResult = makeHTMLAndSendMail(email, certificationNumber);
        //메일 발송결과가 "ok"가 아닐 경우 redis저장 스킵
        if (!"ok".equals(sendResult)) {
            return new EmailCertificationResponseDTO(email, certificationNumber);
        }
        certificationNumberRepository.saveCertificationNumber(email, certificationNumber);
        return new EmailCertificationResponseDTO(email, certificationNumber);
    }

    private void checkNameBeforeUpdatePassword(String email, String name) {
        if (StringUtils.isNotBlank(name)) {
            if (!memberRepository.existsByEmailAndName(email, name)) {
                throw new MemberCheckException();
            }
        }
    }

    private String makeHTMLAndSendMail(String email, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        if (mimeMessage == null) {
            System.out.println("local환경 바로 리턴");
            return "env => local";
        }
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(email);
        helper.setSubject("다가치 이메일 인증 번호입니다.");

        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 안녕하세요 다가치입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 복사해 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= content+"</strong><div><br/> ";
        msgg+= "</div>";
        helper.setText(msgg, true);//내용

        mailSender.send(mimeMessage);
        return "ok";
    }

    public String sendEmailForUpdatePassword(String email) throws MessagingException {
        String password = createCode();
        String result = makePasswordAndSendMail(email, password);
        if (!"ok".equals(result)) {
            return "fail";
        }
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomRespBodyException("이메일정보를 확인해주세요"));
        member.setPassword(passwordEncoder.encode(password));
        return "ok";

    }

    private String makePasswordAndSendMail(String email, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        if (mimeMessage == null) {
            System.out.println("local환경 바로 리턴");
            return "env => local";
        }
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(email);
        helper.setSubject("다가치 변경 패스워드 발송 메일입니다.");

        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 안녕하세요 다가치입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>변경된 패스워드는 다음과 같습니다.<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>패스워드 입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "PASSWORD : <strong>";
        msgg+= content+"</strong><div><br/> ";
        msgg+= "</div>";
        helper.setText(msgg, true);//내용

        mailSender.send(mimeMessage);
        return "ok";
    }
}