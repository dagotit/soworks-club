package com.gmail.dlwk0807.dagotit.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class CertificationNumberRepository {

    private final StringRedisTemplate redisTemplate;
    private static final long EMAIL_VERIFICATION_LIMIT_IN_SECONDS = 180;

    public void saveCertificationNumber(String email, String certificationNumber) {
        redisTemplate.opsForValue()
                .set(email, certificationNumber,
                        Duration.ofSeconds(EMAIL_VERIFICATION_LIMIT_IN_SECONDS));
    }

    public String getCertificationNumber(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    public void removeCertificationNumber(String email) {
        redisTemplate.delete(email);
    }

    public boolean hasKey(String email) {
        Boolean keyExists = redisTemplate.hasKey(email);
        return keyExists != null && keyExists;
    }
}