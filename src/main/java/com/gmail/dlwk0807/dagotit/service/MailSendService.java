package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.dto.EmailCertificationResponse;
import com.gmail.dlwk0807.dagotit.repository.CertificationNumberDao;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

import static com.gmail.dlwk0807.dagotit.util.CommonUtil.createCode;

@Service
@RequiredArgsConstructor
public class MailSendService {

    private final JavaMailSender mailSender;
    private final CertificationNumberDao certificationNumberDao;

    public EmailCertificationResponse sendEmailForCertification(String email) throws NoSuchAlgorithmException, MessagingException {

        String certificationNumber = createCode();
        String content = certificationNumber;
        certificationNumberDao.saveCertificationNumber(email, certificationNumber);
        sendMail(email, content);
        return new EmailCertificationResponse(email, certificationNumber);
    }

    private void sendMail(String email, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(email);
        helper.setSubject("MAIL_TITLE_CERTIFICATION");
        helper.setText(content);
        mailSender.send(mimeMessage);
    }
}