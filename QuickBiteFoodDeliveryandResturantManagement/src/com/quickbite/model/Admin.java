package com.quickbite.model;

public class Admin extends User {
    private int accessLevel;

    public Admin(int id, String name, String email, long mobile, int accessLevel) {
        super(id, name, email, mobile);
        this.accessLevel = accessLevel;
    }

    @Override
    public void login() {
        System.out.println(">> Admin [" + getName() + "] logged into Command Center.");
    }

    @Override
    public void logout() {
        System.out.println("<< Admin [" + getName() + "] logged out.");
    }
}