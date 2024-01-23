package com.gmail.dlwk0807.dagotit.controller;

import com.gmail.dlwk0807.dagotit.dto.member.MemberUpdateDTO;
import com.gmail.dlwk0807.dagotit.dto.member.RequestPasswordDTO;
import com.gmail.dlwk0807.dagotit.service.MemberService;
import com.gmail.dlwk0807.dagotit.util.SecurityUtil;
import com.gmail.dlwk0807.dagotit.vo.ApiMessageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
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

    @PostMapping("/update-password")
    public ApiMessageVO updatePassword(@RequestBody RequestPasswordDTO requestPasswordDto) {

        memberService.updatePassword(requestPasswordDto);

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody("")
                .respCode(OK_RESP_CODE)
                .build();
    }

    @PostMapping("/member-update")
    public ApiMessageVO memberUpdate(@RequestBody MemberUpdateDTO memberUpdateDto) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(memberService.memberUpdate(memberUpdateDto))
                .respCode(OK_RESP_CODE)
                .build();
    }


    @PostMapping(value = "/profile-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiMessageVO upload(@RequestPart(value = "file") MultipartFile file) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(memberService.profileUpload(file))
                .respCode(OK_RESP_CODE)
                .build();
    }


}
