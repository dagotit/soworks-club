package com.gmail.dlwk0807.dagachi.controller;

import com.gmail.dlwk0807.dagachi.dto.group.GroupAttendRequestDTO;
import com.gmail.dlwk0807.dagachi.service.GroupAttendService;
import com.gmail.dlwk0807.dagachi.vo.ApiMessageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group-attend")
@Tag(name = "GROUP-ATTEND API", description = "모임참가 관련")
public class GroupAttendController {

    private final GroupAttendService groupAttendService;

    @Operation(summary = "모임 신청")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @PostMapping("/apply")
    public ApiMessageVO applyGroupAttend(@Valid @RequestBody GroupAttendRequestDTO groupAttendRequestDto) throws Exception {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupAttendService.applyGroupAttend(groupAttendRequestDto))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "모임참가 리스트")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @GetMapping("/list")
    public ApiMessageVO listGroupAttend(@RequestParam Long groupId) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupAttendService.listGroupAttend(groupId))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "모임참가취소")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @PostMapping("/cancel")
    public ApiMessageVO cancelGroupAttend(@Valid @RequestBody GroupAttendRequestDTO groupAttendRequestDto) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(groupAttendService.cancelGroupAttend(groupAttendRequestDto))
                .respCode(OK_RESP_CODE)
                .build();
    }

}