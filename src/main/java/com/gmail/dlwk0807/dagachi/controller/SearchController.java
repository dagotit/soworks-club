package com.gmail.dlwk0807.dagachi.controller;

import com.gmail.dlwk0807.dagachi.service.SearchService;
import com.gmail.dlwk0807.dagachi.vo.ApiMessageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
@Tag(name = "SEARCH API", description = "통합검색 관련")
public class SearchController {
    private final SearchService searchService;

    @Operation(summary = "통합검색")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @GetMapping("/list")
    public ApiMessageVO search(@RequestParam String keyword) {

        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(searchService.search(keyword))
                .respCode(OK_RESP_CODE)
                .build();
    }

}
