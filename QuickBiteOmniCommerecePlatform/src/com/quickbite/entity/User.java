package com.quickbite.entity;


public abstract class User {
 protected int userId;
 protected String name;
 protected String email;
 protected Address address;

 public User(int userId, String name, String email, Address address) {
     this.userId = userId;
     this.name = name;
     this.email = email;
     this.address = address;
 }

 public int getUserId() { return userId; }
 public String getName() { return name; }
 public String getEmail() { return email; }
 public Address getAddress() { return address; }
}