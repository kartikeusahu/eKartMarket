package com.example.ekartmarket.Model;

public class CartProductModel{
    int CartId;
    String EmailId;

    int Pid;

    int Unit;

    String Date;

    String ProductName;

    int SaleRate;

    String PackSize;
    String Picture;

    public int getCartId() {
        return CartId;
    }

    public void setCartId(int cartId) {
        CartId = cartId;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public int getPid() {
        return Pid;
    }

    public void setPid(int pid) {
        Pid = pid;
    }

    public int getUnit() {
        return Unit;
    }

    public void setUnit(int unit) {
        Unit = unit;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getSaleRate() {
        return SaleRate;
    }

    public void setSaleRate(int saleRate) {
        SaleRate = saleRate;
    }

    public String getPackSize() {
        return PackSize;
    }

    public void setPackSize(String packSize) {
        PackSize = packSize;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }
}