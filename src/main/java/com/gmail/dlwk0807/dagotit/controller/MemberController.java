package com.gmail.dlwk0807.dagotit.controller;

import com.gmail.dlwk0807.dagotit.dto.MemberDeleteDto;
import com.gmail.dlwk0807.dagotit.dto.MemberRequestDto;
import com.gmail.dlwk0807.dagotit.dto.MemberUpdateDto;
import com.gmail.dlwk0807.dagotit.dto.RequestPassword;
import com.gmail.dlwk0807.dagotit.service.MemberService;
import com.gmail.dlwk0807.dagotit.util.SecurityUtil;
import com.gmail.dlwk0807.dagotit.vo.ApiMessageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/me")
    public ApiMessageVO findMemberInfoById() {
        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()))
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

    @PostMapping("/update-password")
    public ApiMessageVO updatePassword(@RequestBody RequestPassword requestPassword) {

        memberService.updatePassword(requestPassword);

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody("")
                .respCode(OK_RESP_CODE)
                .build();
    }

    @PostMapping("/member-update")
    public ApiMessageVO memberUpdate(@RequestBody MemberUpdateDto memberUpdateDto) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(memberService.memberUpdate(memberUpdateDto))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @PostMapping("/member-delete")
    public ApiMessageVO memberDelete(@RequestBody MemberDeleteDto memberDeleteDto, @AuthenticationPrincipal User user) {

        memberService.memberDelete(memberDeleteDto, user);

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody("")
                .respCode(OK_RESP_CODE)
                .build();
    }
}
