package com.gmail.dlwk0807.dagachi.dto.category;

import com.gmail.dlwk0807.dagachi.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponseDTO {
    private Long id;
    private String name;

    public static CategoryResponseDTO of(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName()
        );
    }

}
