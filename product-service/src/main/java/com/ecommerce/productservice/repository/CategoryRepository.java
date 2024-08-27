package com.ecommerce.productservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerce.productservice.model.Category;

public interface CategoryRepository extends MongoRepository<Category,String>{
	Optional<Category> findByname(String name);
}
