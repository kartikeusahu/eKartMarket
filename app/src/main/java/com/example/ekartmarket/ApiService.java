package com.example.ekartmarket;

import com.example.ekartmarket.Model.CartModel;
import com.example.ekartmarket.Model.CategoryModel;
import com.example.ekartmarket.Model.ProductModel;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("getallcategories")
    Call<ArrayList<CategoryModel>> getallcategorys();

    @GET("getallproducts")
    Call<ArrayList<ProductModel>>getallproducts();
    @GET("getproductbyid")
    Call<ProductModel>getproductbyid(@Query("id")int id);
    @GET("getproductsbycategory")
    Call<ArrayList<ProductModel>>getproductsbycategory(@Query("catid")int catid);

    @GET("addtocart")
    Call<String>addtocart(@Query("emailid")String emailid,@Query("pid")int pid,@Query("unit")int unit);

    @GET("getcartdetails")
    Call<CartModel>getcartdetails(@Query("emailid")String emailid);

    @GET("insertorder")
    Call<String>insertorder(@Query("name")String name, @Query("mobno")String mobno,
                            @Query("houseno")String houseno,
                            @Query("landmark")String landmark,
                            @Query("fulladdress")String fulladdress,
                            @Query("pincode")String pincode,
                            @Query("emailid")String emailid,
                            @Query("totalitems")int totalitems,
                            @Query("totalbillamount")double totalbillamount,
                            @Query("orderstatus")String orderstatus,
                            @Query("orderdate") String orderdate,
                            @Query("deliverydate") String deliverydate);

}
