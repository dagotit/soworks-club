package com.gmail.dlwk0807.dagotit.controller;

import com.gmail.dlwk0807.dagotit.dto.group.GroupAttachFileRequestDTO;
import com.gmail.dlwk0807.dagotit.dto.group.GroupRequestDTO;
import com.gmail.dlwk0807.dagotit.dto.group.GroupResponseDTO;
import com.gmail.dlwk0807.dagotit.service.GroupService;
import com.gmail.dlwk0807.dagotit.vo.ApiMessageVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_MSG;

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
    public ApiMessageVO createGroup(@RequestPart(value = "group") GroupRequestDTO groupRequestDto,
                                    @RequestPart(value = "file", required = false) MultipartFile groupImageFile) throws Exception {
        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.saveGroup(groupRequestDto, groupImageFile))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @PostMapping("/update")
    public ApiMessageVO updateGroup(@RequestPart(value = "group") GroupRequestDTO groupRequestDto,
                                    @RequestPart(value = "file", required = false) MultipartFile groupImageFile) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.updateGroup(groupRequestDto, groupImageFile))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @PostMapping("/update-attach-file")
    public ApiMessageVO updateAttachFile(@RequestPart(value = "group") GroupAttachFileRequestDTO groupAttachFileRequestDTO,
                                    @RequestPart(value = "groupFiles", required = false) List<MultipartFile> groupFiles,
                                    @AuthenticationPrincipal User user) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.updateGroupAttachFile(groupAttachFileRequestDTO, groupFiles, user))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @PostMapping("/delete")
    public ApiMessageVO deleteGroup(@Valid @RequestBody GroupRequestDTO groupRequestDto) {

        groupService.deleteGroup(groupRequestDto);

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody("")
                .respCode(OK_RESP_CODE)
                .build();
    }

    @GetMapping("/group-list")
    public ApiMessageVO listGroup(@RequestParam int month, @RequestParam int year) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.listGroup(month, year))
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