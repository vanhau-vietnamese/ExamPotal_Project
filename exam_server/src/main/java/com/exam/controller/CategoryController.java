package com.exam.controller;

import com.exam.model.Category;
import com.exam.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/add")
    public Category addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }
    @GetMapping("/{id}")
    public Category getCategory(@PathVariable("id") Long id){
        return categoryService.getCategory(id);
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllCategories(){
        return categoryService.getAllCategories();
    }
}
