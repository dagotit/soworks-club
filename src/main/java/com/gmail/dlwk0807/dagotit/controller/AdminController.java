package com.gmail.dlwk0807.dagotit.controller;

import com.gmail.dlwk0807.dagotit.dto.AdminSendAlarmDto;
import com.gmail.dlwk0807.dagotit.dto.MemberDeleteDto;
import com.gmail.dlwk0807.dagotit.dto.MemberUpdateDto;
import com.gmail.dlwk0807.dagotit.dto.RequestPasswordDto;
import com.gmail.dlwk0807.dagotit.service.AdminService;
import com.gmail.dlwk0807.dagotit.service.MemberService;
import com.gmail.dlwk0807.dagotit.util.SecurityUtil;
import com.gmail.dlwk0807.dagotit.vo.ApiMessageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/send-alarm")
    public ApiMessageVO sendAlarm(@RequestParam AdminSendAlarmDto adminSendAlarmDto) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(adminService.sendAlarm(adminSendAlarmDto))
                .respCode(OK_RESP_CODE)
                .build();
    }

}
