package com.gmail.dlwk0807.dagachi.controller;

import com.gmail.dlwk0807.dagachi.dto.group.*;
import com.gmail.dlwk0807.dagachi.service.GroupService;
import com.gmail.dlwk0807.dagachi.vo.ApiMessageVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/info")
    public ApiMessageVO info(@RequestParam Long groupId) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.info(groupId))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @PostMapping("/save")
    public ApiMessageVO createGroup(@RequestPart(value = "group") GroupSaveRequestDTO groupSaveRequestDto,
                                    @RequestPart(value = "file", required = false) MultipartFile groupImageFile) throws Exception {
        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.saveGroup(groupSaveRequestDto, groupImageFile))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @PostMapping("/update")
    public ApiMessageVO updateGroup(@RequestPart(value = "group") GroupUpdateRequestDTO groupRequestDto,
                                    @RequestPart(value = "file", required = false) MultipartFile groupImageFile) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.updateGroup(groupRequestDto, groupImageFile))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @PostMapping("/update-attach-file")
    public ApiMessageVO updateAttachFile(@RequestPart(value = "group") GroupAttachFileRequestDTO groupAttachFileRequestDTO,
                                    @RequestPart(value = "groupFiles", required = false) List<MultipartFile> groupFiles) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.updateGroupAttachFile(groupAttachFileRequestDTO, groupFiles))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @GetMapping("/update-status")
    public ApiMessageVO updateGroupStatus(GroupStatusRequestDTO groupStatusRequestDTO) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.updateGroupStatus(groupStatusRequestDTO))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @PostMapping("/delete")
    public ApiMessageVO deleteGroup(@Valid @RequestBody GroupDeleteRequestDTO groupRequestDto) {

        groupService.deleteGroup(groupRequestDto);

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody("")
                .respCode(OK_RESP_CODE)
                .build();
    }

    @GetMapping("/group-list")
    public ApiMessageVO listGroup(GroupListRequestDTO groupListRequestDTO) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.listGroup(groupListRequestDTO))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @GetMapping("/join-group")
    public ApiMessageVO joinGroup(@RequestParam Long groupId, @AuthenticationPrincipal User user) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.joinGroup(groupId, user))
                .respCode(OK_RESP_CODE)
                .build();
    }

}