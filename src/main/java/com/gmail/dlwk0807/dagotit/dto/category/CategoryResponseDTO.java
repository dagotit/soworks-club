package com.gmail.dlwk0807.dagotit.dto.category;

import com.gmail.dlwk0807.dagotit.entity.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private Long depth;
    private Long upCategoryId;

    public static CategoryResponseDTO of(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDepth(),
                category.getUpCategory().getId()
        );
    }

}
