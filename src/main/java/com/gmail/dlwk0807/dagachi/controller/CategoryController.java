package com.gmail.dlwk0807.dagachi.controller;

import com.gmail.dlwk0807.dagachi.dto.category.CategoryRequestDTO;
import com.gmail.dlwk0807.dagachi.service.CategoryService;
import com.gmail.dlwk0807.dagachi.vo.ApiMessageVO;
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

    @GetMapping("/list")
    public ApiMessageVO list(@RequestParam(required = false) Long upCategoryId) {
        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(categoryService.list(upCategoryId))
                .respCode(OK_RESP_CODE)
                .build();
    }

    @PostMapping("/save")
    public ApiMessageVO save(@RequestBody List<CategoryRequestDTO> categoryRequestDTO) {
        return ApiMessageVO.builder()
                .respMsg(OK_RESP_MSG)
                .respBody(categoryService.save(categoryRequestDTO))
                .respCode(OK_RESP_CODE)
                .build();
    }
}
