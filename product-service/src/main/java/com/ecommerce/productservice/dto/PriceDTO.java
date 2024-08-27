package com.ecommerce.productservice.dto;

public class PriceDTO {

    private String id;
    private String productId;
    private double basePrice;
    private double discount;
    private double finalPrice;

    // Constructors
    public PriceDTO() {
    }

    public PriceDTO(String id, String productId, double basePrice, double discount, double finalPrice) {
        this.id = id;
        this.productId = productId;
        this.basePrice = basePrice;
        this.discount = discount;
        this.finalPrice = finalPrice;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
