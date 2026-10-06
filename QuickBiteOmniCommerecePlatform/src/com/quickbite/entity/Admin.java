package com.quickbite.entity;

public class Admin extends User {
	private String department;
	private int accessLevel;

	public Admin(int userId, String name, String email, Address address, String department, int accessLevel) {
		super(userId, name, email, address);
		this.department = department;
		this.accessLevel = accessLevel;
	}

	public String getDepartment() {
		return department;
	}

	public int getAccessLevel() {
		return accessLevel;
	}
}