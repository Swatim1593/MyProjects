package com.quickbite.entity;

import java.util.Objects;

public class Category implements Comparable<Category> {
	private int categoryId;
	private String categoryName;
	
	/*Category electronics = new Category(1, "Electronics");
	Category food = new Category(2, "Food");
	Category grocery = new Category(3, "Grocery");
	Category fashion = new Category(4, "Fashion");*/

	public Category(int categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Category category = (Category) o;
		return categoryId == category.categoryId;
	}


	public int hashCode() {
		return Objects.hash(categoryId);
	}

	
	public int compareTo(Category o) {
		return Integer.compare(this.categoryId, o.categoryId);
	}

	
	public String toString() {
		return categoryName;
	}
}