package com.gmail.dlwk0807.dagachi.controller;

import com.gmail.dlwk0807.dagachi.dto.calendar.CalendarRequestDTO;
import com.gmail.dlwk0807.dagachi.service.CalendarService;
import com.gmail.dlwk0807.dagachi.vo.ApiMessageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/calendar")
public class CalendarController {
    private final CalendarService calendarService;

    @GetMapping("/list")
    public ApiMessageVO list(CalendarRequestDTO calendarRequestDTO) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(calendarService.list(calendarRequestDTO))
                .respCode(OK_RESP_CODE)
                .build();
    }

}
