package com.ecommerce.productservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.productservice.model.Price;
import com.ecommerce.productservice.repository.PriceRepository;

@Service
public class PriceService {
	
	@Autowired
	private PriceRepository priceRepository;
	
	public Optional<Price> getPriceByProductId(String productId) {
        return priceRepository.findByProductId(productId);
        
    }

    public Price updatePrice(String productId, double basePrice, double discount) {
        return priceRepository.findByProductId(productId)
                .map(price -> {
                    price.setBasePrice(basePrice);
                    price.setDiscount(discount);
                    price.setFinalPrice(basePrice - discount);
                    return priceRepository.save(price);
                })
                .orElseThrow(() -> new RuntimeException("Price not found for product id: " + productId));
    }

    public Price createPrice(Price price) {
        price.setFinalPrice(price.getBasePrice() - price.getDiscount());
        return priceRepository.save(price);
    }

    public void deletePrice(String id) {
        priceRepository.deleteById(id);
    }
}
