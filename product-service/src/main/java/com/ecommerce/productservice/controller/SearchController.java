package com.ecommerce.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.productservice.dto.ProductResponseDTO;
import com.ecommerce.productservice.service.ProductService;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private ProductService productService;

    // Search products by category name
    @GetMapping("/category")
    public List<ProductResponseDTO> searchProductsByCategory(@RequestParam String categoryName) {
        return productService.searchProductsByCategory(categoryName);
    }

    // Get products by name
    @GetMapping("/getproduct")
    public List<ProductResponseDTO> getProductByName(@RequestParam String name) {
        return productService.getProductsByName(name);
    }

    // Filter products by price range
    @GetMapping("/price")
    public List<ProductResponseDTO> filterProductsByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        return productService.filterProductsByPriceRange(minPrice, maxPrice);
    }

    // Search products by brand name
    @GetMapping("/brand")
    public List<ProductResponseDTO> searchProductsByBrand(@RequestParam String brand) {
        return productService.searchProductsByBrand(brand);
    }

    // Filter products by inventory quantity range
    @GetMapping("/inventory")
    public List<ProductResponseDTO> filterProductsByInventoryQuantity(@RequestParam int minQuantity, @RequestParam int maxQuantity) {
        return productService.filterProductsByInventoryQuantity(minQuantity, maxQuantity);
    }
}
