package com.gmail.dlwk0807.dagotit.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import hello.api.entity.Users;
import hello.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//권한이나 인증이 필요한 주소를 타게 될 때
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private final UserRepository userRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		log.info("JwtAuthorizationFilter 인가 : 진입");

		//JWT 토큰이 헤더가 존재한다면, if문 안으로 들어가서 토큰 검증을 시작한다.
		String header = request.getHeader(JwtProperties.HEADER_STRING);
		log.info("header exist = {}", header);
		if (header != null && header.startsWith(JwtProperties.TOKEN_PREFIX)) {
			String token = request.getHeader(JwtProperties.HEADER_STRING)
				.replace(JwtProperties.TOKEN_PREFIX, "");

			// 토큰 자체 검증
			// 토큰 자체에 인가 정보가 담겨 있기 때문에 AuthenticationManager가 필요 없다.
			//내가 가지고 있는 secret키를 사용해, JWT 토큰이 올바른 지 확인
			String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
				.getClaim("username").asString();

			if (username != null) {
				Users user = userRepository.findByUsername(username).orElseThrow();

				// 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
				// 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장하기!!
				PrincipalDetails principalDetails = new PrincipalDetails(user);
				Authentication authentication = new UsernamePasswordAuthenticationToken(
					principalDetails, // 나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
					null, // 패스워드는 모르니까 null 처리
					principalDetails.getAuthorities());

				// 강제로 시큐리티의 세션에 접근하여 값 저장
				//서명을 통해서 ahthentication을 객체를 만들어준다.
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		//다음 과정으로 넘어간다.
		//JWT 토큰이 맞다면 Security 세션에 값이 있는 상태에서 로그인 상태로 다음 절차 진행
		//JWT 토큰 인증 실패했다면, Security 세션이 빈 상태로 다음 절차 진행 -> 에러 발생
		doFilter(request, response, chain);
	}
}