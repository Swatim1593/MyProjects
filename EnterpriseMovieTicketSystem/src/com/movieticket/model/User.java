package com.movieticket.model;

import com.movieticket.enums.UserRole;

public abstract class User {
	protected final String id;
	protected final String name;
	protected final String email;
	protected final String phone;
	protected final String passwordHash;
	protected final UserRole role;

	public User(String id, String name, String email, String phone, String passwordHash, UserRole role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.passwordHash = passwordHash;
		this.role = role;

	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public UserRole getRole() {
		return role;
	}

	public boolean validatePassword(String password) {
		return this.passwordHash.equals(password);
	}
}
