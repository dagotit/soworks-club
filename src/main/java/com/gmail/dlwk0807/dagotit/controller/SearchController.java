package com.gmail.dlwk0807.dagotit.controller;

import com.gmail.dlwk0807.dagotit.service.SearchService;
import com.gmail.dlwk0807.dagotit.vo.ApiMessageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagotit.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/list")
    public ApiMessageVO search(@RequestParam String keyword) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(searchService.search(keyword))
                .respCode(OK_RESP_CODE)
                .build();
    }

}
