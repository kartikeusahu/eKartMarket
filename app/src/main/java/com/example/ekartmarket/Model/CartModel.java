package com.example.ekartmarket.Model;

import java.util.ArrayList;

public class CartModel {

    private ArrayList<CartProductModel>CartItems;
    private int TotalItems;
    private  int TotalAmount;
    private  int DiscountAmount;
    private  int mrp;

    public int getMrp() {
        return mrp;
    }

    public void setMrp(int mrp) {
        this.mrp = mrp;
    }



    public ArrayList<CartProductModel> getCartItems() {
        return CartItems;
    }

    public void setCartItems(ArrayList<CartProductModel> cartItems) {
        CartItems = cartItems;
    }

    public int getTotalItems() {
        return TotalItems;
    }

    public void setTotalItems(int totalItems) {
        TotalItems = totalItems;
    }

    public int getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        TotalAmount = totalAmount;
    }

    public int getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        DiscountAmount = discountAmount;
    }
}
