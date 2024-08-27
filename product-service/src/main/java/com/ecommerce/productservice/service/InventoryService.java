package com.ecommerce.productservice.service;

import com.ecommerce.productservice.model.Inventory;
import com.ecommerce.productservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	public Optional<Inventory> getInventoryByProductId(String productId) {
		return inventoryRepository.findByProductId(productId);
	}

	public Inventory updateInventory(String productId, int quantity) {
		return inventoryRepository.findByProductId(productId).map(inventory -> {
			inventory.setQuantity(quantity);
			return inventoryRepository.save(inventory);
		}).orElseThrow(() -> new RuntimeException("Inventory not found for product id: " + productId));
	}

	public Inventory createInventory(Inventory inventory) {
		return inventoryRepository.save(inventory);
	}

	public void deleteInventory(String id) {
		inventoryRepository.deleteById(id);
	}
}
