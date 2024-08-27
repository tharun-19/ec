package com.ecommerce.productservice.util;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class SearchSpecification {

	public Query getProductsByCategory(String categoryName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("category.name").is(categoryName));
		return query;
	}

	public Query getProductsByPriceRange(double minPrice, double maxPrice) {
		Query query = new Query();
		query.addCriteria(Criteria.where("price.finalPrice").gte(minPrice).lte(maxPrice));
		return query;
	}

	public Query getProductsByCriteria(String categoryName, double minPrice, double maxPrice) {
		Criteria criteria = new Criteria();

		if (categoryName != null && !categoryName.isEmpty()) {
			criteria.and("category.name").is(categoryName);
		}
		if (minPrice > 0 && maxPrice > 0) {
			criteria.and("price.finalPrice").gte(minPrice).lte(maxPrice);
		}

		return new Query(criteria);
	}

}
