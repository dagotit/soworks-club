package com.gmail.dlwk0807.dagachi.core.exception;

import com.gmail.dlwk0807.dagachi.vo.ApiMessageVO;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

    @Autowired
    private ConfigurableEnvironment env;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownError(Exception e) {
        String respBody = "";
//        if (getProfile()) {
            respBody = e.getMessage();
//        }
        log.error(this.getClass().getName(), e);
        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_001")
                .respBody(respBody)
                .respMsg("일반 오류 입니다.[자세한 내용은 서버에 문의 주세요.]").build(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> unknownError(RuntimeException e) {
        String respBody = e.getMessage();
        log.error(this.getClass().getName(), e);

        if (e.getLocalizedMessage().contains("timed-out and fallback")) {
            return new ResponseEntity<>(
                    new ApiMessageVO(HttpStatus.GATEWAY_TIMEOUT.value() + ""
                            , HttpStatus.GATEWAY_TIMEOUT.name()
                            , e.getMessage())
                    , HttpStatus.GATEWAY_TIMEOUT);
        }

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_002")
                .respBody(respBody)
                .respMsg("런타임 오류 입니다.[자세한 내용은 서버에 문의 주세요]").build(), HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> requiredParamError(MissingServletRequestParameterException e) {
        String respBody = e.getMessage();

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_003")
                .respBody(respBody)
                .respMsg("필수 파라미터 오류 입니다.[자세한 내용은 서버에 문의 주세요]").build(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder respBody = new StringBuilder();
//        if (getProfile()) {
            e.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                respBody.append("{" + fieldName + "} " + errorMessage);
            });
//        }
        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_004")
                .respBody(respBody.toString())
                .respMsg("파라미터 유효성 오류 입니다.[자세한 내용은 서버에 문의 주세요]").build(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> bindException(BindException e) {
        String respBody = e.getMessage();
        String firstMsg = e.getBindingResult().getAllErrors().get(0).getCodes()[0];
        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_005")
                .respBody(respBody)
                .respMsg("파라미터 바인딩 오류 입니다.[자세한 내용은 서버에 문의 주세요]: " + firstMsg).build(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<?> resourceAccessError(ResourceAccessException e) {
        String respBody = e.getMessage();
        log.error(this.getClass().getName(), e);

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_006")
                .respBody(respBody)
                .respMsg("외부 리소스 조회 오류 입니다.[자세한 내용은 서버에 문의 주세요]").build(), HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> BadCredentialsException(BadCredentialsException e) {
        String respBody = e.getMessage();
        log.error(this.getClass().getName(), e);

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_007")
                .respBody(respBody)
                .respMsg("잘못된 로그인 정보입니다.").build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicationMember.class)
    public ResponseEntity<?> duplicationMember(DuplicationMember e) {
        String respBody = e.getMessage();
        log.error(this.getClass().getName(), e);

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_008")
                .respBody(respBody)
                .respMsg("중복된 회원정보입니다.").build(), HttpStatus.OK);
    }

    @ExceptionHandler(InvalidCertificationNumberException.class)
    public ResponseEntity<?> invalidCertificationNumberException(InvalidCertificationNumberException e) {
        String respBody = e.getMessage();
        log.error(this.getClass().getName(), e);

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_009")
                .respBody(respBody)
                .respMsg("문자가 일치하지 않습니다.").build(), HttpStatus.OK);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<?> emailNotFoundException(EmailNotFoundException e) {
        String respBody = e.getMessage();
        log.error(this.getClass().getName(), e);

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_010")
                .respBody(respBody)
                .respMsg("이메일을 찾을 수 없습니다.").build(), HttpStatus.OK);
    }

    @ExceptionHandler(DuplicationEmailSenderException.class)
    public ResponseEntity<?> duplicationEmailSenderException(DuplicationEmailSenderException e) {
        String respBody = e.getMessage();
        log.error(this.getClass().getName(), e);

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_011")
                .respBody(respBody)
                .respMsg("발송된 메일이 있습니다. 확인해주세요").build(), HttpStatus.OK);
    }


    @ExceptionHandler(MailAuthenticationException.class)
    public ResponseEntity<?> mailAuthenticationException(MailAuthenticationException e) {
        String respBody = e.getMessage();
        log.error(this.getClass().getName(), e);

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_012")
                .respBody(respBody)
                .respMsg("메일 권한인증 실패입니다.").build(), HttpStatus.OK);
    }

    @ExceptionHandler(MemberCheckException.class)
    public ResponseEntity<?> mailAuthenticationException(MemberCheckException e) {
        String respBody = e.getMessage();
        log.error(this.getClass().getName(), e);

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_013")
                .respBody(respBody)
                .respMsg("회원정보가 존재하지 않습니다.").build(), HttpStatus.OK);
    }

    @ExceptionHandler(AuthenticationNotMatchException.class)
    public ResponseEntity<?> authenticationNotMatchException(AuthenticationNotMatchException e) {
        String respBody = e.getMessage();
        log.error(this.getClass().getName(), e);

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_014")
                .respBody(respBody)
                .respMsg("변경 권한이 존재하지 않습니다.").build(), HttpStatus.OK);
    }

    @ExceptionHandler(AttendDuplicationException.class)
    public ResponseEntity<?> attendDuplicationException(AttendDuplicationException e) {
        String respBody = e.getMessage();
        log.error(this.getClass().getName(), e);

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_015")
                .respBody(respBody)
                .respMsg("이미 출석체크 하셨습니다.").build(), HttpStatus.OK);
    }

    @ExceptionHandler(DuplicationGroup.class)
    public ResponseEntity<?> duplicationGroup(DuplicationGroup e) {
        String respBody = e.getMessage();
        log.error(this.getClass().getName(), e);

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_016")
                .respBody(respBody)
                .respMsg("이미 생성된 모임이 존재합니다.").build(), HttpStatus.OK);
    }

    @ExceptionHandler(DuplicationGroupAttend.class)
    public ResponseEntity<?> duplicationGroupAttend(DuplicationGroupAttend e) {
        String respBody = e.getMessage();
        log.error(this.getClass().getName(), e);

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_017")
                .respBody(respBody)
                .respMsg("이미 모임신청을 하셨습니다.").build(), HttpStatus.OK);
    }

    @ExceptionHandler(CustomRespBodyException.class)
    public ResponseEntity<?> customRespBodyException(CustomRespBodyException e) {
        String respBody = e.getMessage();
        log.error(this.getClass().getName(), e);

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_018")
                .respBody(respBody)
                .respMsg("오류가 발생했습니다. 메세지를 확인해주세요.").build(), HttpStatus.OK);
    }

    @ExceptionHandler(AccessDeniedCustomException.class)
    public ResponseEntity<?> accessDeniedCustomException(AccessDeniedCustomException e) {
        String respBody = e.getMessage();
        log.error(this.getClass().getName(), e);

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_019")
                .respBody(respBody)
                .respMsg("접근권한이 없습니다.").build(), HttpStatus.OK);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException e) {
        String respBody = e.getMessage();
        log.error(this.getClass().getName(), e);

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_020")
                .respBody(respBody)
                .respMsg("요청값이 정확하지 않습니다").build(), HttpStatus.OK);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException e) {
        String respBody = "이메일 중복을 확인해주세요.";
        log.error(this.getClass().getName(), e);

        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_021")
                .respBody(respBody)
                .respMsg("무결성 조건에 위배됩니다.").build(), HttpStatus.OK);
    }

}
