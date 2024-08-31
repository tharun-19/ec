package com.ecommerce.productservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerce.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product,String> {
//	Optional<Product> findByName(String name);
//	List<Product> findByCategoryName(String categoryName);
	List<Product> findByName(String name);
}
