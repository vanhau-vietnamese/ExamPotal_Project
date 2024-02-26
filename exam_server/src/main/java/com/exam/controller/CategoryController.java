package com.exam.controller;

import com.exam.model.exam.Category;
import com.exam.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    // add category
    @PostMapping("/")
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        return ResponseEntity.ok(categoryService.addCategory(category));
    }

    // get category
    @GetMapping(path = "/{categoryId}")
    public Category getCategory(@PathVariable("categoryId") Long categoryId){
        return categoryService.getCategory(categoryId);
    }

    // get all category
    @GetMapping(path = "/")
    public ResponseEntity<?> getCategories(){
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @PutMapping("/")
    public Category updateCategory(@RequestBody Category category){
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Long categoryId){
        categoryService.deleteCategory(categoryId);
    }

}
