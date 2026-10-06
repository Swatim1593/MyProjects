package com.quickbite.model;

public class Wallet {
    private double balance;

    public Wallet(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() { return balance; }

    public void credit(double amount) {
        this.balance += amount;
    }

    public boolean debit(double amount) {
        if (balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
}