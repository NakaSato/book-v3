package com.bookstore.customer;

public class Customer {
    public enum CustomerType {
        GENERAL, VIP
    }

    private String customerId;
    private String username;
    private CustomerType customerType;
    private int loyaltyPoints;

    public Customer(String customerId, String username, CustomerType customerType) {
        this.customerId = customerId;
        this.username = username;
        this.customerType = customerType;
        this.loyaltyPoints = 0;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getUsername() {
        return username;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public boolean isVIP() {
        return this.customerType == CustomerType.VIP;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
        System.out.println(username + " earned " + points + " loyalty points. Total points: " + this.loyaltyPoints);
    }

    @Override
    public String toString() {
        return "ID: " + customerId + ", Username: '" + username + "', Type: " + customerType + ", Loyalty Points: " + loyaltyPoints;
    }
}
