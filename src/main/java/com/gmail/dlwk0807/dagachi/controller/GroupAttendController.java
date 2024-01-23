package com.gmail.dlwk0807.dagachi.controller;

import com.gmail.dlwk0807.dagachi.dto.group.GroupAttendRequestDTO;
import com.gmail.dlwk0807.dagachi.service.GroupAttendService;
import com.gmail.dlwk0807.dagachi.vo.ApiMessageVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group-attend")
public class GroupAttendController {

    private final GroupAttendService groupAttendService;

    @PostMapping("/apply")
    public ApiMessageVO applyGroupAttend(@Valid @RequestBody GroupAttendRequestDTO groupAttendRequestDto) throws Exception {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupAttendService.applyGroupAttend(groupAttendRequestDto))
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
    public ApiMessageVO cancelGroupAttend(@Valid @RequestBody GroupAttendRequestDTO groupAttendRequestDto) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupAttendService.cancelGroupAttend(groupAttendRequestDto))
                .respCode(OK_RESP_CODE)
                .build();
    }

}