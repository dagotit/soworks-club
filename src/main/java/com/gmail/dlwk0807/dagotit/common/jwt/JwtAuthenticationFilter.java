package com.gmail.dlwk0807.dagotit.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.api.dto.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

// API/login url로 post 요청을 하게 되면, 해당 필터가 실행된다.
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager;

	@Override
	// Authentication 객체 만들어서 리턴해야 한다.(AuthenticationManager를 통해서)
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException {

		log.info("JwtAuthenticationFilter 로그인 : 진입");
		//로그인 요청 시 들어온 데이터를 객체로 변환
		ObjectMapper om = new ObjectMapper();
		UserLoginRequest userLoginRequest = null;
		try {
			userLoginRequest = om.readValue(request.getInputStream(), UserLoginRequest.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//해당 객체로 로그인 시도를 위한 유저네임패스워드 authenticationToken 생성
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(
				userLoginRequest.getUsername(),
				userLoginRequest.getPassword());


		// authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
		// loadUserByUsername(토큰의 첫번째 파라미터) 를 호출하고
		// UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
		// UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
		// Authentication 객체를 만들어서 필터체인으로 리턴해준다.
		Authentication authentication =
			authenticationManager.authenticate(authenticationToken);
		// Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
		// Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
		// 결론은 인증 프로바이더에게 알려줄 필요가 없음.

		//위 영역이 성공했다면, session영역에 authenticaion 객체가 저장된다->로그인이 성공
		return authentication;
	}

	@Override
	// 로그인 인증 성공하면 들어오는 메소드
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult)  {
		//Authentication에 있는 정보로 JWT Token 생성해서 response에 담아주기
		PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();
		String jwtToken = JWT.create()
			.withSubject(principalDetailis.getUsername())
			.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
			.withClaim("id", principalDetailis.getUser().getId())
			.withClaim("username", principalDetailis.getUser().getUsername())
			.sign(Algorithm.HMAC512(JwtProperties.SECRET));

		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
	}
}