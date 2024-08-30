package com.ecommerce.productservice.service;

import java.util.List;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;


import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;


@Service
public class SearchService {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

//	public List<Product> searchProductsByCategory(String categoryName) {
//		return productRepository.findByCategoryName(categoryName);
//	}
	
	public List<Product> searchProductsByCategory(String categoryName) {
        // Create the Aggregation
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.lookup("categories", "category", "_id", "categoryDetails"),
                Aggregation.unwind("categoryDetails"),
                Aggregation.match(Criteria.where("categoryDetails.name").is(categoryName))
        );

        // Get the default AggregationOperationContext
        AggregationOperationContext context = Aggregation.DEFAULT_CONTEXT;

        // Log the aggregation pipeline
        Document aggregationPipeline = agg.toDocument("products", context);
        logger.info("Executing Aggregation: {}", aggregationPipeline.toJson());

        // Execute the aggregation
        AggregationResults<Product> results = mongoTemplate.aggregate(agg, "products", Product.class);

        return results.getMappedResults();
    }

	

	public List<Product> filterProductsByPriceRange(double minPrice, double maxPrice) {
		return productRepository.findAll().stream().filter(product -> product.getPrice().getFinalPrice() >= minPrice
				&& product.getPrice().getFinalPrice() <= maxPrice).toList();
	}
}
