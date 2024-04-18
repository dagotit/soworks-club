package com.gmail.dlwk0807.dagachi.controller;

import com.gmail.dlwk0807.dagachi.dto.admin.AdminSendAlarmDTO;
import com.gmail.dlwk0807.dagachi.dto.member.MemberDeleteDTO;
import com.gmail.dlwk0807.dagachi.service.AdminService;
import com.gmail.dlwk0807.dagachi.service.MemberService;
import com.gmail.dlwk0807.dagachi.vo.ApiMessageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@Tag(name = "ADMIN API", description = "관리자 전용")
public class AdminController {
    private final AdminService adminService;
    private final MemberService memberService;

    @Operation(summary = "알람보내기")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @PostMapping("/send-alarm")
    public ApiMessageVO sendAlarm(@RequestBody List<AdminSendAlarmDTO> adminSendAlarmDtoList) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(adminService.sendAlarm(adminSendAlarmDtoList))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "회원삭제")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @PostMapping("/member-delete")
    public ApiMessageVO memberDelete(@RequestBody MemberDeleteDTO memberDeleteDTO) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(memberService.memberDelete(memberDeleteDTO))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "이메일로 회원찾기")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @GetMapping("/{email}")
    public ApiMessageVO findMemberInfoByEmail(@PathVariable String email) {
        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(memberService.findMemberInfoByEmail(email))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "회원일괄업로드")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @PostMapping(value = "/member-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiMessageVO memberUpload(@RequestPart("file") MultipartFile file) throws IOException {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(adminService.memberUpload(file))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "회원일괄조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @GetMapping("/member-list")
    public ApiMessageVO memberList(@RequestParam(required = false) String name) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(adminService.memberList(name))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "회원업로드용 템플릿 다운로드")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @PostMapping(value = "/template", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiMessageVO template(HttpServletResponse res) throws IOException {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(adminService.template(res))
                .respCode(OK_RESP_CODE)
                .build();
    }
}
