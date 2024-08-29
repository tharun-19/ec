package com.ecommerce.productservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.service.ProductService;
@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public List<Product> getAllProducts(){
		return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public Optional<Product> getProductById(@PathVariable String id){
		return productService.getProductById(id);
	}
	
	@PostMapping("/create")
	public List<Product> createProducts(@RequestBody List<Product> products) {
	    List<Product> savedProducts = new ArrayList<>();
	    for (Product product : products) {
	        savedProducts.add(productService.createProduct(product));
	    }
	    return savedProducts;
	}

//	
//	@PostMapping("/create")
//	public Product createProduct(@RequestBody Product product) {
//		System.out.println("create product is called");
//		return productService.createProduct(product);
//	}
//	
//	@PostMapping("/creates")
//	public List<Product> createProducts(@RequestBody List<Product> products) {
//	    return productService.createProducts(products);
//	}

	
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
		return productService.updateProduct(id, product);
	}
	
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable String id) {
		System.out.println("product delete is called "+id);
		productService.deleteProduct(id); 
	}
}

