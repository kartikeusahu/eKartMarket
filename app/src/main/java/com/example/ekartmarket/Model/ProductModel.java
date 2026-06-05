package com.example.ekartmarket.Model;

public class ProductModel {
    private int Id;
    private int CatId;
    private  String Name;
    private  String Details;
    private int MRP;
    private int SaleRate;
    private String Pack_size;
    private String Picture;

    public int getStockQty() {
        return StockQty;
    }

    public void setStockQty(int stockQty) {
        StockQty = stockQty;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public String getPack_size() {
        return Pack_size;
    }

    public void setPack_size(String pack_size) {
        Pack_size = pack_size;
    }

    public int getSaleRate() {
        return SaleRate;
    }

    public void setSaleRate(int saleRate) {
        SaleRate = saleRate;
    }

    public int getMRP() {
        return MRP;
    }

    public void setMRP(int MRP) {
        this.MRP = MRP;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCatId() {
        return CatId;
    }

    public void setCatId(int catId) {
        CatId = catId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    private  int StockQty;
}
