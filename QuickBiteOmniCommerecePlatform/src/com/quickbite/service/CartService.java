package com.quickbite.service;

import com.quickbite.entity.OrderItem;
import com.quickbite.entity.Product;
import java.util.*;

public class CartService {
    private Map<Integer, OrderItem> cartMap = new HashMap<>();

    public void addProduct(Product product, int qty) {
        if (cartMap.containsKey(product.getProductId())) {
            OrderItem existing = cartMap.get(product.getProductId());
            cartMap.put(product.getProductId(), new OrderItem(product, existing.getQuantity() + qty));
        } else {
            cartMap.put(product.getProductId(), new OrderItem(product, qty));
        }
    }

    public void removeProduct(int productId) { cartMap.remove(productId); }
    public List<OrderItem> getCartItems() { return new ArrayList<>(cartMap.values()); }
    public void clearCart() { cartMap.clear(); }
    public double getCartTotal() { return cartMap.values().stream().mapToDouble(OrderItem::getSubtotal).sum(); }
}