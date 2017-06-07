package edu.wmich.cs.stock;

import java.io.Serializable;

public class Stock implements Serializable {
    private int mUserId;
    private String mStock;
    private double mPrice;
    private int mQuantity;
    private double mValue;

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getStock() {
        return mStock;
    }

    public void setStock(String stock) {
        mStock = stock;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public double getValue() {
        return mQuantity * mPrice;
    }

    public void setValue(double value) {
        mValue = value;
    }
}
