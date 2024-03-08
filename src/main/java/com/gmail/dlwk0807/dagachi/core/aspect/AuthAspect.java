package com.gmail.dlwk0807.dagachi.core.aspect;

import com.gmail.dlwk0807.dagachi.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagachi.util.AuthUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AuthAspect {

    private final AuthUtils authUtils;

    public AuthAspect(AuthUtils authUtils) {
        this.authUtils = authUtils;
    }

    @Around("execution(* com.gmail.dlwk0807.dagachi.controller..*.*(..)) && !execution(* com.gmail.dlwk0807.dagachi.controller.AuthController.*(..))")
    public Object checkAuthentication(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!authUtils.isAdmin()) {
            // 인증 확인 로직
            Long currentMemberId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (arg instanceof AOPMemberIdDTO) { // requestDto가 MemberRequestDto 타입인 경우
                    AOPMemberIdDTO requestDto = (AOPMemberIdDTO) arg;
                    if (!currentMemberId.equals(requestDto.getMemberId())) {
                        throw new AuthenticationNotMatchException();
                    }
                }
            }
        }
        return joinPoint.proceed();
    }
}