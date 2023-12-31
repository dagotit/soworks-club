package com.gmail.dlwk0807.dagotit.controller;

import com.gmail.dlwk0807.dagotit.dto.GroupAttendRequestDto;
import com.gmail.dlwk0807.dagotit.service.GroupAttendService;
import com.gmail.dlwk0807.dagotit.vo.ApiMessageVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group-attend")
public class GroupAttendController {

    private final GroupAttendService groupAttendService;

    @PostMapping("/apply")
    public ApiMessageVO applyGroupAttend(@Valid @RequestBody GroupAttendRequestDto groupAttendRequestDto, @AuthenticationPrincipal User user) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupAttendService.applyGroupAttend(groupAttendRequestDto, user))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @GetMapping("/list")
    public ApiMessageVO listGroupAttend(@RequestParam Long groupId) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupAttendService.listGroupAttend(groupId))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @PostMapping("/cancel")
    public ApiMessageVO cancelGroupAttend(@Valid @RequestBody GroupAttendRequestDto groupAttendRequestDto, @AuthenticationPrincipal User user) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupAttendService.cancelGroupAttend(groupAttendRequestDto, user))
                .respCode(OK_RESP_CODE)
                .build();
    }

}