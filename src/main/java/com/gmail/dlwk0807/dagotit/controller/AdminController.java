package com.gmail.dlwk0807.dagotit.controller;

import com.gmail.dlwk0807.dagotit.dto.admin.AdminSendAlarmDTO;
import com.gmail.dlwk0807.dagotit.dto.member.MemberDeleteDTO;
import com.gmail.dlwk0807.dagotit.dto.member.MemberUpdateDTO;
import com.gmail.dlwk0807.dagotit.service.AdminService;
import com.gmail.dlwk0807.dagotit.service.MemberService;
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
    private final MemberService memberService;

    @GetMapping("/send-alarm")
    public ApiMessageVO sendAlarm(@RequestParam AdminSendAlarmDTO adminSendAlarmDto) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(adminService.sendAlarm(adminSendAlarmDto))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @PostMapping("/member-delete")
    public ApiMessageVO memberDelete(@RequestBody MemberDeleteDTO memberDeleteDTO) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(memberService.memberDelete(memberDeleteDTO))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @GetMapping("/{email}")
    public ApiMessageVO findMemberInfoByEmail(@PathVariable String email) {
        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(memberService.findMemberInfoByEmail(email))
                .respCode(OK_RESP_CODE)
                .build();
    }

}
