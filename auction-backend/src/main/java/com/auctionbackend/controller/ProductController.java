package com.auctionbackend.controller;

import com.auctionbackend.entity.Category;
import com.auctionbackend.entity.Product;
import com.auctionbackend.repository.CategoryRepository;
import com.auctionbackend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public Page<Product> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long price,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 5);
        return productRepository.search(name, price, categoryId, pageable);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        validate(product);
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        validate(request);

        Category category = categoryRepository.findById(
                request.getCategory().getId()
        ).orElseThrow(() -> new RuntimeException("Loại sản phẩm không tồn tại"));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStatus(request.getStatus());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @DeleteMapping
    public void deleteMany(@RequestBody List<Long> ids) {
        System.out.println("DELETE MANY: " + ids);
        productRepository.deleteAllById(ids);
    }

    private void validate(Product p) {
        if (p.getName() == null || p.getName().length() < 5 || p.getName().length() > 50)
            throw new RuntimeException("Tên sản phẩm phải từ 5–50 ký tự");

        if (p.getPrice() == null || p.getPrice() < 100000)
            throw new RuntimeException("Giá phải >= 100.000");

        if (p.getCategory() == null)
            throw new RuntimeException("Loại sản phẩm không được rỗng");
    }
}

