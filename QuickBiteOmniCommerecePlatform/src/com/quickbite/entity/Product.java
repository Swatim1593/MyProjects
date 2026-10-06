package com.quickbite.entity;

import java.util.Objects;

public class Product {

	private int productId;
	private String name;
	private double price;
	private int stock;
	private Category category;
	
	/*System.out.println("\n1. CONFIGURING CATALOG & CATEGORIES...");
	Category electronics = new Category(1, "Electronics");
	Category food = new Category(2, "Food");
	Category grocery = new Category(3, "Grocery");
	Category fashion = new Category(4, "Fashion");
	
	

	Product p1 = new Product(101, "Sony Wireless Headphones", 12000.0, 15, electronics);
	Product p2 = new Product(102, "Hyderabadi Chicken Biryani", 350.0, 50, food);
	Product p3 = new Product(103, "Organic Olive Oil 1L", 850.0, 30, grocery);
	Product p4 = new Product(104, "Levi's Denim Jacket", 4500.0, 10, fashion);*/

	public Product(int productId, String name, double price, int stock, Category category) {
		super();
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.category = category;
	}

	public int getProductId() {
		return productId;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}
	
	

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Category getCategory() {
		return category;
	}
	
	public boolean equals(Object o) {
		if(this==o)return true;
		if(o==null||getClass()!=o.getClass())return false;
		Product product=(Product)o;
		return productId==product.productId;
	}
	public int hashCode() {
		return Objects.hash(productId);
	}

	@Override
	public String toString() {
		return String.format("SKU #%d: %-20s | Cat: %-12s | Price: ₹%.2f | Stock: %3d", productId, name,
				category.getCategoryName(), price, stock);
	}

}