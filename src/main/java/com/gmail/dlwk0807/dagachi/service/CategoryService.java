package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagachi.dto.category.CategoryRequestDTO;
import com.gmail.dlwk0807.dagachi.dto.category.CategoryResponseDTO;
import com.gmail.dlwk0807.dagachi.entity.Category;
import com.gmail.dlwk0807.dagachi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDTO> list() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(CategoryResponseDTO::of).collect(Collectors.toList());
    }

}
