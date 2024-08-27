package com.ecommerce.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;

@Service
public class SearchService {

	private ProductRepository productRepository;

	public List<Product> searchProductsByCategory(String categoryName) {
		return productRepository.findByCategoryName(categoryName);
	}

	public List<Product> filterProductsByPriceRange(double minPrice, double maxPrice) {
		return productRepository.findAll().stream().filter(product -> product.getPrice().getFinalPrice() >= minPrice
				&& product.getPrice().getFinalPrice() <= maxPrice).toList();
	}
}
