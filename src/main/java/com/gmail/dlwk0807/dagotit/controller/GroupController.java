package com.gmail.dlwk0807.dagotit.controller;

import com.gmail.dlwk0807.dagotit.dto.EmailCertificationRequestDto;
import com.gmail.dlwk0807.dagotit.dto.GroupRequestDto;
import com.gmail.dlwk0807.dagotit.service.GroupService;
import com.gmail.dlwk0807.dagotit.vo.ApiMessageVO;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final GroupService groupService;


    @PostMapping("/save")
    public ApiMessageVO saveGroup(@RequestBody GroupRequestDto groupRequestDto) {
        groupService.saveGroup(groupRequestDto);

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody("")
                .respCode(OK_RESP_CODE)
                .build();
    }

}