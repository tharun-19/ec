package com.ecommerce.productservice.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getProductById(String id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No product found with ID: " + id));
	}

	public List<Product> getProductsByName(String name) {
		List<Product> products = productRepository.findByName(name);
		if (products.isEmpty()) {
			throw new RuntimeException("No product found with the name: " + name);
		}
		return products;
	}

	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	public Product updateProduct(String id, Product productDetails) {
		return productRepository.findById(id).map(product -> {
			product.setName(productDetails.getName());
			product.setDescription(productDetails.getDescription());
			product.setBrand(productDetails.getBrand());
			product.setPrice(productDetails.getPrice());
			product.setFinalPrice(productDetails.getFinalPrice());
			product.setInventories(productDetails.getInventories());
			product.setCategory(productDetails.getCategory());

			return productRepository.save(product);
		}).orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
	}

	public void deleteProduct(String id) {
		if (!productRepository.existsById(id)) {
			throw new ProductNotFoundException("Product not found with id: " + id);
		}
		productRepository.deleteById(id);
	}

	// Search products by category name
	public List<Product> searchProductsByCategory(String categoryName) {
		Query query = new Query(Criteria.where("category.name").is(categoryName));
		logger.info("Executing Query: {}", query);
		return mongoTemplate.find(query, Product.class);
	}

	// Filter products by price range
	public List<Product> filterProductsByPriceRange(double minPrice, double maxPrice) {
		Query query = new Query(Criteria.where("price.finalPrice").gte(minPrice).lte(maxPrice));
		logger.info("Executing Query: {}", query);
		return mongoTemplate.find(query, Product.class);
	}

	// Search products by brand name
	public List<Product> searchProductsByBrand(String brand) {
		Query query = new Query(Criteria.where("brand").is(brand));
		logger.info("Executing Query: {}", query);
		return mongoTemplate.find(query, Product.class);
	}

	// Filter products by inventory quantity range
	public List<Product> filterProductsByInventoryQuantity(int minQuantity, int maxQuantity) {
		Query query = new Query(Criteria.where("inventories.quantity").gte(minQuantity).lte(maxQuantity));
		logger.info("Executing Query: {}", query);
		return mongoTemplate.find(query, Product.class);
	}

}
