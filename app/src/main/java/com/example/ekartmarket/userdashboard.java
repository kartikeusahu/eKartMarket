package com.example.ekartmarket;

import static android.widget.Toast.LENGTH_LONG;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ekartmarket.Adapter.CategoryAdapter;
import com.example.ekartmarket.Adapter.ProductAdapter;
import com.example.ekartmarket.Model.CategoryModel;
import com.example.ekartmarket.Model.ProductModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class userdashboard extends AppCompatActivity {

    DrawerLayout userdrawer_layout;

    Toolbar user_toolbat;

    NavigationView user_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_userdashboard);

        userdrawer_layout=findViewById(R.id.userdrawer_layout);
        user_toolbat=findViewById(R.id.user_toolbar);
        user_nav=findViewById(R.id.user_nav);

        //set toolbar

        setSupportActionBar(user_toolbat);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,userdrawer_layout,user_toolbat,R.string.Welcome_msg,R.string.Welcome_msg);
        userdrawer_layout.addDrawerListener(toggle);
        toggle.syncState();


        //set onclickevent of all menu items of nevigation view
        user_nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId()==R.id.user_adashboard)
                {
                    Intent i= new Intent(getApplicationContext(),userdashboard.class);
                    startActivity(i);
                } else if (menuItem.getItemId()==R.id.user_acart) {
                    Intent i= new Intent(getApplicationContext(),UserCart.class);
                    startActivity(i);
                }else if (menuItem.getItemId()==R.id.user_ahistory) {
                    Intent i= new Intent(getApplicationContext(),UserHistory.class);
                    startActivity(i);
                }else if (menuItem.getItemId()==R.id.user_aprofile) {
                    Intent i= new Intent(getApplicationContext(),UserProfile.class);
                    startActivity(i);
                }
                else if (menuItem.getItemId()==R.id.user_alogout)
                {
                    FirebaseAuth.getInstance().signOut();
                    Intent i= new Intent(getApplicationContext(),UserLogin.class);
                    startActivity(i);
                }
                return true;
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://edge.techpile.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        Call<ArrayList<CategoryModel>> category = api.getallcategorys();
        category.enqueue(new Callback<ArrayList<CategoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {
                if (response.isSuccessful())
                {
                    ArrayList<CategoryModel> data = response.body();
                    RecyclerView recycle_cat = findViewById(R.id.recycler_category);
                    recycle_cat.setLayoutManager(new LinearLayoutManager(userdashboard.this,LinearLayoutManager.HORIZONTAL,false));
                    CategoryAdapter adapter = new CategoryAdapter(getApplicationContext(),data);
                    recycle_cat.setAdapter(adapter);

                    //when api is called succesfully, handle the response
                    // Toast.makeText(getApplicationContext(),data.size()+"",LENGTH_LONG).show();
                }
                else
                {
                    //when api fails to call server error occ
                    Toast.makeText(getApplicationContext(),"error in api",LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(), LENGTH_LONG).show();
         }



});
        Call<ArrayList<ProductModel>> products = api.getallproducts();
        products.enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if (response.isSuccessful())
                {
                    ArrayList<ProductModel> product=response.body();
                    RecyclerView recycle_product=findViewById(R.id.recycler_product);
                    recycle_product.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                    ProductAdapter adapter = new ProductAdapter(getApplicationContext(),product);
                    recycle_product.setAdapter(adapter);
                    //Toast.makeText(getApplicationContext(),"ok",LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(getApplicationContext(),"not ok" ,LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
            Toast.makeText(getApplicationContext(),t.getMessage(), LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            Intent i= new Intent(userdashboard.this,UserLogin.class);
            startActivity(i);
        }
    }

}