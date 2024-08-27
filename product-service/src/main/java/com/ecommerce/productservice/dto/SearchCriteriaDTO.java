package com.ecommerce.productservice.dto;

public class SearchCriteriaDTO {

    private String categoryName;
    private double minPrice;
    private double maxPrice;

    // Constructors
    public SearchCriteriaDTO() {
    }

    public SearchCriteriaDTO(String categoryName, double minPrice, double maxPrice) {
        this.categoryName = categoryName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    // Getters and Setters
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
