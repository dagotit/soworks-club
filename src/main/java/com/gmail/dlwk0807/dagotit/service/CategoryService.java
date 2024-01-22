package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagotit.dto.category.CategoryRequestDTO;
import com.gmail.dlwk0807.dagotit.dto.category.CategoryResponseDTO;
import com.gmail.dlwk0807.dagotit.entity.Category;
import com.gmail.dlwk0807.dagotit.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDTO> list(Long upCategoryId) {
        List<Category> allByUpCategoryId = categoryRepository.findAll();
        return allByUpCategoryId.stream().map(CategoryResponseDTO::of).collect(Collectors.toList());
    }

    public List<Category> save(List<CategoryRequestDTO> categoryRequestDTOs) {
        List<Category> savedCategories = new ArrayList<>();

        for (CategoryRequestDTO categoryRequestDTO : categoryRequestDTOs) {
            Category category = categoryRequestDTO.of(categoryRequestDTO);

            // 부모 카테고리 설정
            if (categoryRequestDTO.getUpCategoryId() != null) {
                Category upCategory = categoryRepository.findById(categoryRequestDTO.getUpCategoryId())
                        .orElseThrow(() -> new CustomRespBodyException(categoryRequestDTO.getUpCategoryId() + "값 존재하지 않음"));
                category.setUpCategory(upCategory);
            }

            // depth 자동 계산
            if (category.getUpCategory() != null) {
                category.setDepth(category.getUpCategory().getDepth() + 1);
            } else {
                category.setDepth(1L);
            }

            savedCategories.add(categoryRepository.save(category));
        }

        return savedCategories;
    }

}
