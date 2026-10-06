package com.movieticket.model;

import java.util.Arrays;
import java.util.List;

import com.movieticket.enums.UserRole;

public class Admin extends User{
	private final List<String>permissions;

	public Admin(String id, String name, String email, String phone, String password) {
		super(id, name, email, phone, password, UserRole.ADMIN);
		this.permissions = Arrays.asList("MANAGE_MOVIES","VIEW_REPORTS");
	}

	public boolean hasPermissions(String permissions) {
		return permissions.contains(permissions);
	}
	
	 

}
