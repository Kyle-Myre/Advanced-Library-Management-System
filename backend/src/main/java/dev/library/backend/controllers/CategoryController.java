package dev.library.backend.controllers;

import java.util.List;

import dev.library.backend.dto.requests.CategoryRequestDto;
import dev.library.backend.dto.response.CategoryResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.library.backend.services.CategoryService;

@RestController
@CrossOrigin 
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/")
    public List<CategoryResponseDto> getCategories() {
        return this.categoryService.getCategories();
    }
    @GetMapping("/{id}")
    public CategoryResponseDto getCategory(@PathVariable("id") Long id) {
        return this.categoryService.getCategory(id);
    }
    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping("/create")
    public CategoryResponseDto createCategory(@RequestBody CategoryRequestDto request) {;
        return this.categoryService.createCategory(request);
    }
    @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping("/update/{id}")
    public CategoryResponseDto updateCategory(@RequestBody CategoryRequestDto request, @PathVariable("id") Long id) {
        return this.categoryService.updateCategory(id , request);
    }
    @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable Long id) {
        this.categoryService.deleteCategory(id);
    }
}