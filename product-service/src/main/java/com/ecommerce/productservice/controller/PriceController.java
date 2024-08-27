package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.model.Price;
import com.ecommerce.productservice.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @GetMapping("/{productId}")
    public Price getPriceByProductId(@PathVariable String productId) {
        return priceService.getPriceByProductId(productId);
    }

    @PutMapping("/admin/{productId}")
    public Price updatePrice(@PathVariable String productId, @RequestParam double basePrice, @RequestParam double discount) {
        return priceService.updatePrice(productId, basePrice, discount);
    }

    @PostMapping("/admin")
    public Price createPrice(@RequestBody Price price) {
        return priceService.createPrice(price);
    }

    @DeleteMapping("/admin/{id}")
    public void deletePrice(@PathVariable String id) {
        priceService.deletePrice(id);
    }
}
