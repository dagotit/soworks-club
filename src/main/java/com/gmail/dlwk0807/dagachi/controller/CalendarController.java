package com.gmail.dlwk0807.dagachi.controller;

import com.gmail.dlwk0807.dagachi.dto.calendar.CalendarRequestDTO;
import com.gmail.dlwk0807.dagachi.service.CalendarService;
import com.gmail.dlwk0807.dagachi.vo.ApiMessageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/calendar")
@Tag(name = "CALENDAR API", description = "캘린더 api")
public class CalendarController {
    private final CalendarService calendarService;

    @Operation(summary = "캘린더조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @GetMapping("/list")
    public ApiMessageVO list(@Valid @ModelAttribute CalendarRequestDTO calendarRequestDTO) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(calendarService.list(calendarRequestDTO))
                .respCode(OK_RESP_CODE)
                .build();
    }

}
