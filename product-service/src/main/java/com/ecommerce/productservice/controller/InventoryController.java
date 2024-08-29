package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.model.Inventory;
import com.ecommerce.productservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/product/{productId}")
    public Inventory getInventoryByProductId(@PathVariable String productId) {
        return inventoryService.getInventoryByProductId(productId);
    }

    @PutMapping("/product/{productId}")
    public Inventory updateInventory(@PathVariable String productId, @RequestParam int quantity) {
        return inventoryService.updateInventory(productId, quantity);
    }

    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryService.createInventory(inventory);
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable String id) {
        inventoryService.deleteInventory(id);
    }
}

