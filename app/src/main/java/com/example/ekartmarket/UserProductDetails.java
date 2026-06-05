package com.example.ekartmarket;

import static android.widget.Toast.LENGTH_LONG;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.ekartmarket.Model.ProductModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProductDetails extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_product_details);
        Intent i=getIntent();
        int id = i.getIntExtra("id",0);
        //    Toast.makeText(this,id+"",LENGTH_LONG).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://edge.techpile.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        Call<ProductModel> idwiseproduct=api.getproductbyid(id);
        idwiseproduct.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if (response.isSuccessful())

                {

                    ProductModel p=response.body();
                    ImageView product_image = findViewById(R.id.details_productimg);
                    TextView product_name = findViewById(R.id.product_productname);
                    TextView product_salerrate = findViewById(R.id.product_selerate);
                    TextView product_mrp = findViewById(R.id.details_productmrp);
                    TextView product_details = findViewById(R.id.product_details);
                    TextView product_packsize = findViewById(R.id.product_productsize);

                    Glide.with(getApplicationContext()).load(p.getPicture()).into(product_image);
                    product_name.setText(p.getName());
                    product_salerrate.setText("Rs."+p.getSaleRate());
                    product_mrp.setText("Rs."+p.getMRP());
                    product_details.setText(p.getDetails());
                    product_packsize.setText(p.getPack_size());
                   // Toast.makeText(getApplicationContext(),"ok",LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"error in api",LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"api is failure",LENGTH_LONG).show();
            }
        });

    }
}