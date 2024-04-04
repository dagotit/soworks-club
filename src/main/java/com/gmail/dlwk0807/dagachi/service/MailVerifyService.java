package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.core.exception.EmailNotFoundException;
import com.gmail.dlwk0807.dagachi.core.exception.InvalidCertificationNumberException;
import com.gmail.dlwk0807.dagachi.repository.CertificationNumberRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MailVerifyService {

    private final CertificationNumberRepository certificationNumberRepository;
    private final MailSendService mailSendService;
    private final Environment environment;

    public void verifyEmail(String email, String certificationNumber) {
        if (!isVerify(email, certificationNumber)) {
            throw new InvalidCertificationNumberException();
        }
        certificationNumberRepository.removeCertificationNumber(email);
    }

    public String verifyEmailAndUpdatePassword(String email, String certificationNumber) throws MessagingException {
        if (!isVerify(email, certificationNumber)) {
            throw new InvalidCertificationNumberException();
        }
        certificationNumberRepository.removeCertificationNumber(email);

        //비밀번호 랜덤문자로 변경 & 이메일 발송
        return mailSendService.sendEmailForUpdatePassword(email);
    }

    private boolean isVerify(String email, String certificationNumber) {
        String profile = environment.getActiveProfiles()[0];
        if ("local".equals(profile)) {
            return true;
        }
        boolean validatedEmail = isEmailExists(email);
        if (!validatedEmail) {
            throw new EmailNotFoundException();
        }
        return (validatedEmail &&
                certificationNumberRepository.getCertificationNumber(email).equals(certificationNumber));
    }

    private boolean isEmailExists(String email) {
        return certificationNumberRepository.hasKey(email);
    }
}