package com.ecommerce.productservice.service;

import com.ecommerce.productservice.exception.InventoryNotFoundException;
import com.ecommerce.productservice.model.Inventory;
import com.ecommerce.productservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory getInventoryByProductId(String productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for product id: " + productId));
    }

    public Inventory updateInventory(String productId, int quantity) {
        return inventoryRepository.findByProductId(productId)
                .map(inventory -> {
                    inventory.setQuantity(quantity);
                    return inventoryRepository.save(inventory);
                })
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for product id: " + productId));
    }

    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public void deleteInventory(String id) {
        if (!inventoryRepository.existsById(id)) {
            throw new InventoryNotFoundException("Inventory not found with id: " + id);
        }
        inventoryRepository.deleteById(id);
    }
}
