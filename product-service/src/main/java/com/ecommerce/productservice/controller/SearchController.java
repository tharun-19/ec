package com.ecommerce.productservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.service.ProductService;

@RestController
@RequestMapping("/api/search")
public class SearchController {



    @Autowired
    private ProductService productService;
    
    // Search products by category name
    @GetMapping("/category")
    public List<Product> searchProductsByCategory(@RequestParam String categoryName) {
        return productService.searchProductsByCategory(categoryName);
    }

    // Get a product by its name
    @GetMapping("/getproduct")
    public List<Product> getProductByName(@RequestParam String name) {
        return productService.getProductsByName(name);
    }
    
    // Filter products by price range
    @GetMapping("/price")
    public List<Product> filterProductsByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        return productService.filterProductsByPriceRange(minPrice, maxPrice);
    }

    // Search products by brand name
    @GetMapping("/brand")
    public List<Product> searchProductsByBrand(@RequestParam String brand) {
        return productService.searchProductsByBrand(brand);
    }

    // Filter products by inventory quantity range
    @GetMapping("/inventory")
    public List<Product> filterProductsByInventoryQuantity(@RequestParam int minQuantity, @RequestParam int maxQuantity) {
        return productService.filterProductsByInventoryQuantity(minQuantity, maxQuantity);
    }
}
