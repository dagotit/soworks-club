package com.gmail.dlwk0807.dagachi.controller;

import com.gmail.dlwk0807.dagachi.service.AttendanceService;
import com.gmail.dlwk0807.dagachi.vo.ApiMessageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @GetMapping("/attend")
    public ApiMessageVO attend() {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(attendanceService.attend())
                .respCode(OK_RESP_CODE)
                .build();
    }

}
