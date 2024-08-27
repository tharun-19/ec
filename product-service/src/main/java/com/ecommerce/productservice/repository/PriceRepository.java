package com.ecommerce.productservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerce.productservice.model.Price;

public interface PriceRepository extends MongoRepository<Price, String>{
	Optional<Price> findByProductId(String productId);
}
