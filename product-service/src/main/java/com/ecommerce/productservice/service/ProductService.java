package com.ecommerce.productservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ecommerce.productservice.dto.ProductRequestDTO;
import com.ecommerce.productservice.dto.ProductResponseDTO;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

   
    public List<ProductResponseDTO> getAllProductDTOs() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public ProductResponseDTO getProductResponseDTOById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No product found with ID: " + id));
        return toResponseDTO(product);
    }

    public ProductResponseDTO createProductFromDTO(ProductRequestDTO dto) {
        Product product = toEntity(dto);
        // If finalPrice is computed, do it here before saving
        product.setFinalPrice(product.getBasePrice() - product.getDiscount());
        Product saved = productRepository.save(product);
        return toResponseDTO(saved);
    }

    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
    
    
    public List<ProductResponseDTO> getProductsByName(String name) {
        List<Product> products = productRepository.findByName(name);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No product found with the name: " + name);
        }
        return products.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<ProductResponseDTO> searchProductsByCategory(String categoryName) {
        Query query = new Query(Criteria.where("categoryName").is(categoryName));
        logger.info("Executing Query: {}", query);
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<ProductResponseDTO> filterProductsByPriceRange(double minPrice, double maxPrice) {
        Query query = new Query(Criteria.where("finalPrice").gte(minPrice).lte(maxPrice));
        logger.info("Executing Query: {}", query);
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<ProductResponseDTO> searchProductsByBrand(String brand) {
        Query query = new Query(Criteria.where("brand").is(brand));
        logger.info("Executing Query: {}", query);
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<ProductResponseDTO> filterProductsByInventoryQuantity(int minQuantity, int maxQuantity) {
        Query query = new Query(Criteria.where("inventories.quantity").gte(minQuantity).lte(maxQuantity));
        logger.info("Executing Query: {}", query);
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    // Private mapper methods (same as given before):
    private Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBrand(dto.getBrand());
        product.setBasePrice(dto.getBasePrice());
        product.setDiscount(dto.getDiscount());
        product.setCategoryName(dto.getCategoryName());
        product.setCategoryDescription(dto.getCategoryDescription());
        product.setStockQuantity(dto.getStockQuantity());
        return product;
    }

    private ProductResponseDTO toResponseDTO(Product product) {
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(product.getId());
        responseDTO.setName(product.getName());
        responseDTO.setDescription(product.getDescription());
        responseDTO.setBrand(product.getBrand());
        responseDTO.setBasePrice(product.getBasePrice());
        responseDTO.setDiscount(product.getDiscount());
        responseDTO.setFinalPrice(product.getFinalPrice());
        responseDTO.setCategoryName(product.getCategoryName());
        responseDTO.setCategoryDescription(product.getCategoryDescription());
        responseDTO.setStockQuantity(product.getStockQuantity());
        return responseDTO;
    }

}
