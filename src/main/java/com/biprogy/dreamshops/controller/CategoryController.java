package com.biprogy.dreamshops.controller;

import com.biprogy.dreamshops.exceptions.AlreadyExitsException;
import com.biprogy.dreamshops.exceptions.ResourceNotFoundException;
import com.biprogy.dreamshops.model.Category;
import com.biprogy.dreamshops.response.ApiResponse;
import com.biprogy.dreamshops.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService iCategoryService;

    @GetMapping("/category/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categoryList = iCategoryService.getAllCategory();
            return ResponseEntity.ok(new ApiResponse("Found!", categoryList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/category/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
        try {
            Category category = iCategoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Success", category));
        } catch (AlreadyExitsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long Id){
        try {
            Category category = iCategoryService.getCategoryById(Id);
            return ResponseEntity.ok(new ApiResponse("Found!", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try {
            Category category = iCategoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found!", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long Id){
        try {
            iCategoryService.deleteCategoryById(Id);
            return ResponseEntity.ok(new ApiResponse("Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/category/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long Id, @RequestBody Category category){
        try {
            Category updateCategory = iCategoryService.updateCategory(category, Id);
            return ResponseEntity.ok(new ApiResponse("Found!", updateCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
