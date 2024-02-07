package com.gmail.dlwk0807.dagachi.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // 람다를 이용
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
