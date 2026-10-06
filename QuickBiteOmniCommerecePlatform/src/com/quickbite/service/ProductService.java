package com.quickbite.service;

import com.quickbite.dao.impl.ProductDAOImpl;
import com.quickbite.entity.Category;
import com.quickbite.entity.Product;
import com.quickbite.exception.ProductNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class ProductService {
    private ProductDAOImpl productDAO;
    private Set<Category> categorySet = new HashSet<>();

    public ProductService(ProductDAOImpl productDAO) { this.productDAO = productDAO; }

    public void addProduct(Product product) {
        productDAO.save(product);
        categorySet.add(product.getCategory());
    }

    public Product getProduct(int id) {
        Product p = productDAO.findById(id);
        if (p == null) throw new ProductNotFoundException("Product #" + id + " not found.");
        return p;
    }

    public List<Product> getAllProducts() { return productDAO.findAll(); }
    public Set<Category> getCategories() { return categorySet; }

    public List<Product> searchByName(String keyword) {
        return productDAO.findAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}