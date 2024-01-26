package com.gmail.dlwk0807.dagachi.controller;

import com.gmail.dlwk0807.dagachi.dto.member.MemberUpdateDTO;
import com.gmail.dlwk0807.dagachi.dto.member.RequestPasswordDTO;
import com.gmail.dlwk0807.dagachi.service.MemberService;
import com.gmail.dlwk0807.dagachi.util.SecurityUtil;
import com.gmail.dlwk0807.dagachi.vo.ApiMessageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Tag(name = "MEMBER API", description = "유저 관련")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "내정보")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 로그인 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @GetMapping("/me")
    public ApiMessageVO findMemberInfoById() {
        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "비밀번호 업데이트")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @PostMapping("/update-password")
    public ApiMessageVO updatePassword(@RequestBody RequestPasswordDTO requestPasswordDto) {

        memberService.updatePassword(requestPasswordDto);

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody("")
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "회원정보 업데이트")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @PostMapping("/member-update")
    public ApiMessageVO memberUpdate(@RequestBody MemberUpdateDTO memberUpdateDto) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(memberService.memberUpdate(memberUpdateDto))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "프로필사진 업데이트")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @PostMapping(value = "/profile-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiMessageVO upload(@RequestPart(value = "file") MultipartFile file) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(memberService.profileUpload(file))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @Operation(summary = "관리자 여부")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @GetMapping("/check-admin")
    public ApiMessageVO checkAdmin() {
        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(memberService.checkAdmin(SecurityUtil.getCurrentMemberId()))
                .respCode(OK_RESP_CODE)
                .build();
    }

}
