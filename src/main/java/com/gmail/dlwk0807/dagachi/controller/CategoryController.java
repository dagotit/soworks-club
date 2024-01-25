package com.gmail.dlwk0807.dagachi.controller;

import com.gmail.dlwk0807.dagachi.dto.category.CategoryRequestDTO;
import com.gmail.dlwk0807.dagachi.service.CategoryService;
import com.gmail.dlwk0807.dagachi.vo.ApiMessageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_CODE;
import static com.gmail.dlwk0807.dagachi.global.CommonConstant.OK_RESP_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 조회")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
    })
    @GetMapping("/list")
    public ApiMessageVO list(@RequestParam(required = false) Long upCategoryId) {
        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(categoryService.list(upCategoryId))
                .respCode(OK_RESP_CODE)
                .build();
    }

//    @Operation(summary = "카테고리 저장")
//    @ApiResponses({
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "OK, 성공"),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "Error Code",description = "Error message",
//                    content = @Content(schema = @Schema(implementation = ApiMessageVO.class))),
//    })
//    @PostMapping("/save")
//    public ApiMessageVO save(@RequestBody List<CategoryRequestDTO> categoryRequestDTO) {
//        return ApiMessageVO.builder()
//                .respMsg(OK_RESP_MSG)
//                .respBody(categoryService.save(categoryRequestDTO))
//                .respCode(OK_RESP_CODE)
//                .build();
//    }
}
