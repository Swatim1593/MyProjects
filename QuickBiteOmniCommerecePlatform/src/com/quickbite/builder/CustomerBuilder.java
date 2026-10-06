package com.quickbite.builder;

import com.quickbite.entity.Address;
import com.quickbite.entity.Customer;

public class CustomerBuilder {
	
	
	private int userId;
	private String name;
	private String email;
	private Address address;
	private String city;
	private boolean premium;
	
	/*Address addr1 = new Address("100 Residency Rd", "Bengaluru", "Karnataka", "560025");
	Address addr2 = new Address("45 Anna Salai", "Chennai", "Tamil Nadu", "600002");

	Customer c1 = new CustomerBuilder().setUserId(1).setName("Karthik Raja").setEmail("karthik@quickbite.in")
			.setAddress(addr1).setCity("Bengaluru").setPremium(true).build();

	Customer c2 = new CustomerBuilder().setUserId(2).setName("Ananya Rao").setEmail("ananya@quickbite.in")
			.setAddress(addr2).setCity("Chennai").setPremium(false).build();*/

	public CustomerBuilder setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	public CustomerBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public CustomerBuilder setEmail(String email) {
		this.email = email;
		return this;
	}

	public CustomerBuilder setAddress(Address address) {
		this.address = address;
		return this;
	}

	public CustomerBuilder setCity(String city) {
		this.city = city;
		return this;
	}

	public CustomerBuilder setPremium(boolean premium) {
		this.premium = premium;
		return this;
	}

	public Customer build() {
		return new Customer(userId, name, email, address, city, premium);
	}
}