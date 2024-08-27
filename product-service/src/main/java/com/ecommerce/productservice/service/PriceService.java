package com.ecommerce.productservice.service;

import com.ecommerce.productservice.exception.PriceNotFoundException;
import com.ecommerce.productservice.model.Price;
import com.ecommerce.productservice.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public Price getPriceByProductId(String productId) {
        return priceRepository.findByProductId(productId)
                .orElseThrow(() -> new PriceNotFoundException("Price not found for product id: " + productId));
    }

    public Price updatePrice(String productId, double basePrice, double discount) {
        return priceRepository.findByProductId(productId)
                .map(price -> {
                    price.setBasePrice(basePrice);
                    price.setDiscount(discount);
                    price.setFinalPrice(basePrice - discount);
                    return priceRepository.save(price);
                })
                .orElseThrow(() -> new PriceNotFoundException("Price not found for product id: " + productId));
    }

    public Price createPrice(Price price) {
        price.setFinalPrice(price.getBasePrice() - price.getDiscount());
        return priceRepository.save(price);
    }

    public void deletePrice(String id) {
        if (!priceRepository.existsById(id)) {
            throw new PriceNotFoundException("Price not found with id: " + id);
        }
        priceRepository.deleteById(id);
    }
}
