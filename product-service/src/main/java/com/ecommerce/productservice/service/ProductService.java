package com.ecommerce.productservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public Optional<Product> getProductById(String id){
		return productRepository.findById(id);
	}
	
	public Optional<Product> getProductByName(String name){
		return productRepository.findByName(name);
	}
	
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}
	
	public Product updateProduct(String id, Product productDetails) {
		return productRepository.findById(id)
				.map(product -> {
					product.setName(productDetails.getName());
					product.setDescription(productDetails.getDescription());
					product.setBasePrice(productDetails.getBasePrice());
					product.setCategory(productDetails.getCategory());
					product.setInventories(productDetails.getInventories());
					product.setPrice(productDetails.getPrice());
					return productRepository.save(product);
				}).orElseThrow(()-> new RuntimeException("Product not found with ID:"+id));
	}

	public void deleteProduct(String id) {
		productRepository.deleteById(id);
	}
	
}
