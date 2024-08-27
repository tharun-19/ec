package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/category")
    public List<Product> searchProductsByCategory(@RequestParam String categoryName) {
        return searchService.searchProductsByCategory(categoryName);
    }

    @GetMapping("/price")
    public List<Product> filterProductsByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        return searchService.filterProductsByPriceRange(minPrice, maxPrice);
    }

    // Additional search/filter endpoints can be added here
}
