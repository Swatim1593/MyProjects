package com.quickbite.service;

import com.quickbite.dao.impl.ProductDAOImpl;
import com.quickbite.entity.Product;
import com.quickbite.exception.InsufficientStockException;

public class InventoryService {
 private ProductDAOImpl productDAO;

 public InventoryService(ProductDAOImpl productDAO) { this.productDAO = productDAO; }

 public synchronized void deductStock(int productId, int qty) {
     Product p = productDAO.findById(productId);
     if (p.getStock() < qty) {
         throw new InsufficientStockException("Stock deficit for SKU: " + p.getName() + " (Avail: " + p.getStock() + ")");
     }
     p.setStock(p.getStock() - qty);
     productDAO.update(p);
 }
}
