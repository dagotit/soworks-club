package com.gmail.dlwk0807.dagachi.core.config;

import com.gmail.dlwk0807.dagachi.core.config.jwt.JwtAccessDeniedHandler;
import com.gmail.dlwk0807.dagachi.core.config.jwt.JwtAuthenticationEntryPoint;
import com.gmail.dlwk0807.dagachi.core.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            // CSRF 설정 Disable
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .exceptionHandling((exceptionHandling) ->
                exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
            )
            .authorizeHttpRequests(a -> {
                a.requestMatchers(new AntPathRequestMatcher("/h2-console/**")
                                , new AntPathRequestMatcher("/favicon.ico")
                                , new AntPathRequestMatcher("/swagger-ui/**")
                                , new AntPathRequestMatcher("context-path/**")
                                , new AntPathRequestMatcher("/api/v1/auth/**")
                                , new AntPathRequestMatcher("/api/v1/mails/**")
                                , new AntPathRequestMatcher("/v3/api-docs/**")
                                ).permitAll();
                a.requestMatchers(new AntPathRequestMatcher("/api/v1/admin/**"))
                        .hasRole("ADMIN");
                a.anyRequest().authenticated();
            })
            // h2-console 을 위한 설정을 추가
            .headers((headers) ->
                    headers.frameOptions((frameOptions) -> frameOptions.sameOrigin())
            )
            // 시큐리티는 기본적으로 세션을 사용
            // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
            .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }
}
