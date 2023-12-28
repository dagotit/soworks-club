package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.core.exception.EmailNotFoundException;
import com.gmail.dlwk0807.dagotit.core.exception.InvalidCertificationNumberException;
import com.gmail.dlwk0807.dagotit.repository.CertificationNumberDao;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MailVerifyService {

    private final CertificationNumberDao certificationNumberDao;
    private final MailSendService mailSendService;

    public void verifyEmail(String email, String certificationNumber) {
        if (!isVerify(email, certificationNumber)) {
            throw new InvalidCertificationNumberException();
        }
        certificationNumberDao.removeCertificationNumber(email);
    }

    public void verifyEmailAndUpdatePassword(String email, String certificationNumber) throws MessagingException {
        if (!isVerify(email, certificationNumber)) {
            throw new InvalidCertificationNumberException();
        }
        certificationNumberDao.removeCertificationNumber(email);

        //비밀번호 랜덤문자로 변경 & 이메일 발송
        mailSendService.sendEmailForUpdatePassword(email);
    }

    private boolean isVerify(String email, String certificationNumber) {
        boolean validatedEmail = isEmailExists(email);
        if (!validatedEmail) {
            throw new EmailNotFoundException();
        }
        return (validatedEmail &&
                certificationNumberDao.getCertificationNumber(email).equals(certificationNumber));
    }

    private boolean isEmailExists(String email) {
        return certificationNumberDao.hasKey(email);
    }
}