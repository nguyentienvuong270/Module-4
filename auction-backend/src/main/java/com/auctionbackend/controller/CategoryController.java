package com.auctionbackend.controller;

import com.auctionbackend.entity.Category;
import com.auctionbackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        if (category.getName() == null || category.getName().isBlank()) {
            throw new RuntimeException("Tên loại sản phẩm không được để trống");
        }
        return categoryRepository.save(category);
    }
}
