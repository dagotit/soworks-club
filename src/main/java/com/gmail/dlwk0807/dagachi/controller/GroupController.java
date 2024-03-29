package com.gmail.dlwk0807.dagachi.controller;

import com.gmail.dlwk0807.dagachi.dto.group.*;
import com.gmail.dlwk0807.dagachi.service.GroupService;
import com.gmail.dlwk0807.dagachi.vo.ApiMessageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
@Tag(name = "GROUP API", description = "모임관련")
public class GroupController {

    private final GroupService groupService;

    @Operation(summary = "모임정보")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @GetMapping("/info")
    public ApiMessageVO info(@RequestParam Long groupId) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.info(groupId))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "모임저장")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiMessageVO createGroup(@Valid @RequestPart(value = "group") GroupSaveRequestDTO groupSaveRequestDto,
                                    @RequestPart(value = "file", required = false) MultipartFile groupImageFile) throws Exception {
        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.saveGroup(groupSaveRequestDto, groupImageFile))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "모임 업데이트")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiMessageVO updateGroup(@RequestPart(value = "group") GroupUpdateRequestDTO groupRequestDto,
                                    @RequestPart(value = "file", required = false) MultipartFile groupImageFile) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.updateGroup(groupRequestDto, groupImageFile))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "모임 첨부파일 업로드")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @PostMapping(value = "/update-attach-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiMessageVO updateAttachFile(@RequestPart(value = "group") GroupAttachFileRequestDTO groupAttachFileRequestDTO,
                                    @RequestPart(value = "groupFiles", required = false) List<MultipartFile> groupFiles) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.updateGroupAttachFile(groupAttachFileRequestDTO, groupFiles))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "모임 상태변경[WAITING, FULL, DONE]")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @PostMapping("/update-status")
    public ApiMessageVO updateGroupStatus(@RequestBody GroupStatusRequestDTO groupStatusRequestDTO) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.updateGroupStatus(groupStatusRequestDTO))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "모임 삭제")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @PostMapping("/delete")
    public ApiMessageVO deleteGroup(@RequestBody GroupDeleteRequestDTO groupDeleteRequestDTO) {

        groupService.deleteGroup(groupDeleteRequestDTO);

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody("")
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "모임 목록조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @GetMapping("/group-list")
    public ApiMessageVO listGroup(@Valid @ModelAttribute GroupListRequestDTO groupListRequestDTO) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.listGroup(groupListRequestDTO))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "최근 본 모임")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @GetMapping("/recent-list")
    public ApiMessageVO recentList() {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.recentList())
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "모임참가자 출석완료")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @PostMapping("/join-done")
    public ApiMessageVO joinDone(@Valid @RequestBody GroupAttendYnRequestDTO groupAttendYnRequestDTO) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.joinDone(groupAttendYnRequestDTO))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "모임참가자 출석실패")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @PostMapping("/join-fail")
    public ApiMessageVO joinFail(@Valid @RequestBody GroupAttendYnRequestDTO groupAttendYnRequestDTO) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupService.joinFail(groupAttendYnRequestDTO))
                .respCode(OK_RESP_CODE)
                .build();
    }

}