package com.speedfeast.core;

public class FoodItem {
	private int foodId;
	private String foodName;
	private double price;
	{
		System.out.println("[Diagnostic]food item instance initilazed block fired");
	}
	public FoodItem() {
	}
	public FoodItem(int foodId,String foodName,double price) {
		this.foodId=foodId;
		this.foodName=foodName;
		this.price=price;
	}
	public int getFoodId() {
		return foodId;
	}
	public String getFoodName() {
		return foodName;
	}
	public double getPrice() {
		return price;
	
		
	}

}
