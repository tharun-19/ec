package com.ecommerce.productservice.util;

import com.ecommerce.productservice.model.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceCalculator {

    public double calculateFinalPrice(double basePrice, double discount) {
        return basePrice - discount;
    }

    public Price calculateAndUpdatePrice(Price price) {
        double finalPrice = calculateFinalPrice(price.getBasePrice(), price.getDiscount());
        price.setFinalPrice(finalPrice);
        return price;
    }
}
