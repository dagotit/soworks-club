package com.gmail.dlwk0807.dagotit.controller;

import com.gmail.dlwk0807.dagotit.dto.group.GroupRequestDTO;
import com.gmail.dlwk0807.dagotit.service.GroupService;
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
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/save")
    public ApiMessageVO saveGroup(@Valid @RequestBody GroupRequestDTO groupRequestDto, @AuthenticationPrincipal User user) throws Exception {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.saveGroup(groupRequestDto, user))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @PostMapping("/update")
    public ApiMessageVO updateGroup(@Valid @RequestBody GroupRequestDTO groupRequestDto, @AuthenticationPrincipal User user) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.updateGroup(groupRequestDto, user))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @PostMapping("/delete")
    public ApiMessageVO deleteGroup(@Valid @RequestBody GroupRequestDTO groupRequestDto, @AuthenticationPrincipal User user) {

        groupService.deleteGroup(groupRequestDto, user);

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody("")
                .respCode(OK_RESP_CODE)
                .build();
    }

    @GetMapping("/list")
    public ApiMessageVO listGroup(@RequestParam int month) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.listGroup(month))
                .respCode(OK_RESP_CODE)
                .build();
    }

}