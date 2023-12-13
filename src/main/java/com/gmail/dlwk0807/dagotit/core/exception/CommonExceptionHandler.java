package com.gmail.dlwk0807.dagotit.core.exception;

import com.gmail.dlwk0807.dagotit.vo.ApiMessageVO;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> unknowError(Exception e) {
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
    public ResponseEntity<?> unknowError(RuntimeException e) {
        String respBody = "";
//        if (getProfile()) {
            respBody = e.getMessage();
//        }
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
        String respBody = "";
//        if (getProfile()) {
            respBody = e.getMessage();
//        }
        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_003")
                .respBody(respBody)
                .respMsg("필수 파라미터 오류 입니다.[자세한 내용은 서버에 문의 주세요]").build(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> invalidParamError(MethodArgumentNotValidException e) {
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
    public ResponseEntity<?> validationParamError(BindException e) {
        String respBody = "";
//        if (getProfile()) {
            respBody = e.getMessage();
//        }
        String firstMsg = e.getBindingResult().getAllErrors().get(0).getCodes()[0];
//        String firstMsg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_005")
                .respBody(respBody)
                .respMsg("파라미터 바인딩 오류 입니다.[자세한 내용은 서버에 문의 주세요]: " + firstMsg).build(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<?> resourceAccessError(ResourceAccessException e) {
        String respBody = "";
//        if (getProfile()) {
            respBody = e.getMessage();
//        }
        log.error(this.getClass().getName(), e);
        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_006")
                .respBody(respBody)
                .respMsg("외부 리소스 조회 오류 입니다.[자세한 내용은 서버에 문의 주세요]").build(), HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> BadCredentialsException(BadCredentialsException e) {
        String respBody = "";
//        if (getProfile()) {
            respBody = e.getMessage();
//        }
        log.error(this.getClass().getName(), e);
        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_007")
                .respBody(respBody)
                .respMsg("잘못된 로그인 정보입니다.").build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicationMember.class)
    public ResponseEntity<?> resourceAccessError(DuplicationMember e) {
        String respBody = "";
//        if (getProfile()) {
            respBody = e.getMessage();
//        }
        log.error(this.getClass().getName(), e);
        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_008")
                .respBody(respBody)
                .respMsg("중복된 회원정보입니다.").build(), HttpStatus.OK);
    }

    @ExceptionHandler(InvalidCertificationNumberException.class)
    public ResponseEntity<?> resourceAccessError(InvalidCertificationNumberException e) {
        String respBody = "";
//        if (getProfile()) {
            respBody = e.getMessage();
//        }
        log.error(this.getClass().getName(), e);
        return new ResponseEntity<ApiMessageVO>(ApiMessageVO.builder()
                .respCode("BIZ_009")
                .respBody(respBody)
                .respMsg("문자가 일치하지 않습니다.").build(), HttpStatus.OK);
    }


    private boolean getProfile() {
        try {
            String profile = env.getActiveProfiles()[0];
            if (StringUtils.isNotEmpty(profile)) {
                if ("local".equals(profile)) {
                    return true;
                } else if ("default".equals(profile)) {
                    return true;
                } else if ("dev".equals(profile)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception exc) {
            log.error(this.getClass().getName(), exc);
        }
        return false;
    }
}
