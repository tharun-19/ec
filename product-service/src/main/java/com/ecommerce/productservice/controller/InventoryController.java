package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.model.Inventory;
import com.ecommerce.productservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/{productId}")
    public Inventory getInventoryByProductId(@PathVariable String productId) {
        return inventoryService.getInventoryByProductId(productId);
    }

    @PutMapping("/admin/{productId}")
    public Inventory updateInventory(@PathVariable String productId, @RequestParam int quantity) {
        return inventoryService.updateInventory(productId, quantity);
    }

    @PostMapping("/admin")
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryService.createInventory(inventory);
    }

    @DeleteMapping("/admin/{id}")
    public void deleteInventory(@PathVariable String id) {
        inventoryService.deleteInventory(id);
    }
}
