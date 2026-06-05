package com.example.ekartmarket.Model;

public class CategoryModel {
   private int CatId;
  private   String CatName;

    public int getCatId() {
        return CatId;
    }

    public void setCatId(int catId) {
        CatId = catId;
    }

    public String getCatName() {
        return CatName;
    }

    public void setCatName(String catName) {
        CatName = catName;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    private    String Picture;
}
