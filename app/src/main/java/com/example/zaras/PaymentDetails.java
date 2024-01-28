package com.example.zaras;

public class PaymentDetails {

    private String userEmail;
    private String address;
    private String paymentMethod;
    private String otherDetails;

    private double price;  // Change the type to double
    private String status;

    public PaymentDetails() {
        userEmail = "";
        address = "";
        paymentMethod = "";
        otherDetails = "";
        price = 0.0;
    }

    public PaymentDetails(String userEmail, String address, String paymentMethod, String otherDetails, double price, String status) {
        this.userEmail = userEmail;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.otherDetails = otherDetails;
        this.price = price;
        this.status = status;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getAddress() {
        return address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}
