package com.biprogy.dreamshops.controller;

import com.biprogy.dreamshops.model.Product;
import com.biprogy.dreamshops.request.AddProductRequest;
import com.biprogy.dreamshops.request.ProductUpdateRequest;
import com.biprogy.dreamshops.response.ApiResponse;
import com.biprogy.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Port;
import java.lang.module.ResolutionException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("{api.prefix}/products")
public class ProductController {

    private final IProductService iProductService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        try {
            List<Product> products = iProductService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/{Id}")
    public ResponseEntity<ApiResponse> getAllProductsById(@PathVariable Long Id){
        try {
            Product products = iProductService.getProductById(Id);
            return ResponseEntity.ok(new ApiResponse("Success", products));
        } catch (ResolutionException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try {
            Product products = iProductService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Add Product Success!", products));
        } catch (ResolutionException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/update/{Id}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long Id){
        try {
            Product products = iProductService.updateProduct(request, Id);
            return ResponseEntity.ok(new ApiResponse("Update Product Success!", products));
        } catch (ResolutionException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @DeleteMapping("delete/{id}")
    public  ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long Id){
        try {
            iProductService.deleteProductById(Id);
            return ResponseEntity.ok(new ApiResponse("Delete Product Success!", null));
        } catch (ResolutionException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName){
        try {
            List<Product> products = iProductService.getProductsByBrandAndName(brandName, productName);
            if (products.isEmpty()){
                return ResponseEntity.ok(new ApiResponse("Not found product!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Get Product Success!", products));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
        try {
            List<Product> products = iProductService.getProductsByCategoryAndBrand(category, brand);
            if (products.isEmpty()){
                return ResponseEntity.ok(new ApiResponse("Not found product!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Get Product Success!", products));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/by/{name}}")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam String name){
        try {
            List<Product> products = iProductService.getProductByName(name);
            if (products.isEmpty()){
                return ResponseEntity.ok(new ApiResponse("Not found product!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Get Product Success!", products));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/by/category")
    public ResponseEntity<ApiResponse> getProductByCategory(@RequestParam String category){
        try {
            List<Product> products = iProductService.getProductsByCategory(category);
            if (products.isEmpty()){
                return ResponseEntity.ok(new ApiResponse("Not found product!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Get Product Success!", products));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/by/brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand){
        try {
            List<Product> products = iProductService.getProductsByBrand(brand);
            if (products.isEmpty()){
                return ResponseEntity.ok(new ApiResponse("Not found product!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Get Product Success!", products));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/{category}/all")
    public ResponseEntity<ApiResponse> findProductByCategory(@RequestParam String category){
        try {
            List<Product> products = iProductService.getProductsByCategory(category);
            if (products.isEmpty()){
                return ResponseEntity.ok(new ApiResponse("Not found product!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Get Product Success!", products));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}
