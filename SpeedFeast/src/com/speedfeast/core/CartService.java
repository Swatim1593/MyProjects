package com.speedfeast.core;

public class CartService {
	/*
	 * checkoutCart[0] = new FoodItem(401, "Hyderabadi Biryani", 290.0);
	 * checkoutCart[1] = new FoodItem(402, "Paneer Tikka Pizza", 340.0);
	 * checkoutCart[2] = new FoodItem(403, "Crunchy Veg Burger", 120.0);
	 */
	public double calculateBill(FoodItem[] foodItems) {
		double total=0.0;
		if(foodItems==null)
			return total;
		for(int i=0;i<foodItems.length;i++) {
			if (foodItems[i]!=null) {
				total+=foodItems[i].getPrice();
				
			}
		}
		return total;
	}

}
