package com.exam.controller;

import com.exam.dto.request.CategoryRequest;
import com.exam.model.Category;
import com.exam.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody CategoryRequest categoryRequest){
        return categoryService.addCategory(categoryRequest);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable("id") Long id){
        return categoryService.getCategory(id);
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id")Long id, @RequestBody CategoryRequest categoryRequest){
        return categoryService.updateCategory(id, categoryRequest);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId")Long id){
        return categoryService.deleteCategory(id);
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchCategories(@RequestBody Map<String, String> request){
        return categoryService.searchCategories(request);
    }
}
