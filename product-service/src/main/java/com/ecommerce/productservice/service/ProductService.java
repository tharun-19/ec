package com.ecommerce.productservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.model.Category;
import com.ecommerce.productservice.model.Inventory;
import com.ecommerce.productservice.model.Price;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.CategoryRepository;
import com.ecommerce.productservice.repository.InventoryRepository;
import com.ecommerce.productservice.repository.PriceRepository;
import com.ecommerce.productservice.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private PriceRepository priceRepository;
    
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    
    public Optional<Product> getProductById(String id){
        return productRepository.findById(id);
    }
    
    public Optional<Product> getProductByName(String name){
        return productRepository.findByName(name);
    }
    
//    public List<Product> createProducts(List<Product> products) {
//        List<Product> savedProduct = new ArrayList<>();
//        for (Product product : products) {
//            // Handle the Category before saving the product
//            Category category = product.getCategory();
//            if (category.getId() == null) {
//                category = categoryRepository.save(category);
//            }
//            product.setCategory(category);
//            
//            // Handle the Inventory before saving the product
//            List<Inventory> inventories = product.getInventories();
//            for (int i = 0; i < inventories.size(); i++) {
//                Inventory inventory = inventories.get(i);
//                if (inventory.getId() == null) {
//                    inventory = inventoryRepository.save(inventory);
//                    inventories.set(i, inventory); // Update the list with saved inventory
//                }
//            }
//            product.setInventories(inventories);
//            
//            // Handle the Price before saving the product
//            Price price = product.getPrice();
//            if (price.getId() == null) {
//                price = priceRepository.save(price);
//            }
//            product.setPrice(price);
//            
//          
//            savedProduct.add(createProduct(product));
//            
//            // Now that we have the product ID, update the inventories and price with it
//            for (Inventory inventory : inventories) {
//                inventory.setProductId(savedProduct.getId());
//                inventoryRepository.save(inventory);
//            }
//
//            if (price != null) {
//                price.setProductId(savedProduct.getId());
//                priceRepository.save(price);
//            }
//            
//            
//            
//        }
//        return savedProducts;
//    }

    
    
    public Product createProduct(Product product) {
        // Handle the Category before saving the product
        Category category = product.getCategory();
        if (category.getId() == null) {
            category = categoryRepository.save(category);
        }
        product.setCategory(category);

        // Handle the Inventory before saving the product
        List<Inventory> inventories = product.getInventories();
        for (int i = 0; i < inventories.size(); i++) {
            Inventory inventory = inventories.get(i);
            if (inventory.getId() == null) {
                inventory = inventoryRepository.save(inventory);
                inventories.set(i, inventory); // Update the list with saved inventory
            }
        }
        product.setInventories(inventories);

        // Handle the Price before saving the product
        Price price = product.getPrice();
        if (price.getId() == null) {
            price = priceRepository.save(price);
        }
        product.setPrice(price);

        // Save the product to get the generated product ID
        Product savedProduct = productRepository.save(product);

        // Now that we have the product ID, update the inventories and price with it
        for (Inventory inventory : inventories) {
            inventory.setProductId(savedProduct.getId());
            inventoryRepository.save(inventory);
        }

        if (price != null) {
            price.setProductId(savedProduct.getId());
            priceRepository.save(price);
        }

        // Return the final product with all references correctly set
        return savedProduct;
    }

    
    public Product updateProduct(String id, Product productDetails) {
        return productRepository.findById(id)
                .map(product -> {
                    // Handle the Category before updating the product
                    Category category = productDetails.getCategory();
                    if (category.getId() == null) {
                        category = categoryRepository.save(category);
                    }
                    product.setCategory(category);
                    
                    // Handle the Inventory before updating the product
                    List<Inventory> inventories = productDetails.getInventories();
                    for (int i = 0; i < inventories.size(); i++) {
                        Inventory inventory = inventories.get(i);
                        if (inventory.getId() == null) {
                            inventory = inventoryRepository.save(inventory);
                            inventories.set(i, inventory);
                        }
                    }
                    product.setInventories(inventories);

                    // Handle the Price before updating the product
                    Price price = productDetails.getPrice();
                    if (price.getId() == null) {
                        price = priceRepository.save(price);
                    }
                    product.setPrice(price);

                    // Update other product details
                    product.setName(productDetails.getName());
                    product.setDescription(productDetails.getDescription());
                    product.setBasePrice(productDetails.getBasePrice());

                    return productRepository.save(product);
                }).orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }

    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}

