package com.ecommerce.productservice.dto;

import java.util.List;

public class ProductDTO {

    private String id;
    private String name;
    private String description;
    private double basePrice;
    private String category;
    private List<InventoryDTO> inventories;
    private PriceDTO price;

    // Constructors
    public ProductDTO() {
    }

    public ProductDTO(String id, String name, String description, double basePrice, String category, List<InventoryDTO> inventories, PriceDTO price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.category = category;
        this.inventories = inventories;
        this.price = price;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<InventoryDTO> getInventories() {
        return inventories;
    }

    public void setInventories(List<InventoryDTO> inventories) {
        this.inventories = inventories;
    }

    public PriceDTO getPrice() {
        return price;
    }

    public void setPrice(PriceDTO price) {
        this.price = price;
    }
}
