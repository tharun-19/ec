package com.ecommerce.productservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.productservice.dto.ProductRequestDTO;
import com.ecommerce.productservice.dto.ProductResponseDTO;
import com.ecommerce.productservice.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProductDTOs();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable String id) {
        return productService.getProductResponseDTOById(id);
    }

    @PostMapping("/create")
    public List<ProductResponseDTO> createProducts(@RequestBody List<ProductRequestDTO> productRequestDTOs) {
        List<ProductResponseDTO> createdProducts = new ArrayList<>();
        for (ProductRequestDTO dto : productRequestDTOs) {
            createdProducts.add(productService.createProductFromDTO(dto));
        }
        return createdProducts;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }
}
