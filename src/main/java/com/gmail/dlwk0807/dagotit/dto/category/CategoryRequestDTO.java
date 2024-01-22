package com.gmail.dlwk0807.dagotit.dto.category;

import com.gmail.dlwk0807.dagotit.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryRequestDTO {
    private String name;
    private Long depth;
    private Long upCategoryId;

    public static Category of(CategoryRequestDTO requestDTO) {
        return Category.builder()
                .name(requestDTO.getName())
                .depth(requestDTO.getDepth())
                .upCategory(new Category())
                .build();
    }
}
