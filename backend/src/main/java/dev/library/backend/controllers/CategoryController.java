package dev.library.backend.controllers;

import java.util.List;

import dev.library.backend.dto.requests.CategoryRequestDto;
import dev.library.backend.dto.response.CategoryResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
    public CategoryController(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }
    @GetMapping("/")
    public ResponseEntity<List<CategoryResponseDto>> getCategories()
    {
        try {
            return new ResponseEntity<>(this.categoryService.getCategories(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategory(@PathVariable("id") Long id)
    {
        try {
            return new ResponseEntity<>(this.categoryService.getCategory(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDto> createCategory(@Validated @RequestBody CategoryRequestDto request)
    {
        try {
            return new ResponseEntity<>(this.categoryService.createCategory(request), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@RequestBody CategoryRequestDto request, @PathVariable("id") Long id)
    {
        try {
            if (this.categoryService.getCategory(id) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(this.categoryService.updateCategory(id,request) , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            if (this.categoryService.getCategory(id) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            this.categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}