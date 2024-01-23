package com.gmail.dlwk0807.dagachi.dto.category;

import com.gmail.dlwk0807.dagachi.entity.Category;
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
                .build();
    }
}
