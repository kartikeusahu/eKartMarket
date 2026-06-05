package com.example.ekartmarket;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ekartmarket.Adapter.ProductAdapter;
import com.example.ekartmarket.Model.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryWiseProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_wise_product);
        Intent i=getIntent();
        int catid = i.getIntExtra("catid",0);
        String catname = i.getStringExtra("catname");

        TextView categoryname = findViewById(R.id.categoryname);
        categoryname.setText(catname);
       // Toast.makeText(this,catid+""+catname,Toast.LENGTH_LONG).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://edge.techpile.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);

        Call<ArrayList<ProductModel>>products = api.getproductsbycategory(catid);
        products.enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<ProductModel> data = response.body();

                    if (data != null) {
                        RecyclerView recycler = findViewById(R.id.recycler_categorywiseproduct);
                        GridLayoutManager grid = new GridLayoutManager(getApplicationContext(), 2);
                        recycler.setLayoutManager(grid);
                        ProductAdapter adapter = new ProductAdapter(getApplicationContext(), data);
                        recycler.setAdapter(adapter);
                        // Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_LONG).show();
                    }else
                    {
                        Toast.makeText(getApplicationContext(),"No product found",Toast.LENGTH_LONG).show();
                    }
                } else {
                        Toast.makeText(getApplicationContext(), " Error in api", Toast.LENGTH_LONG).show();
                    }
                }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failure on server",Toast.LENGTH_LONG).show();
            }
        });

    }
}