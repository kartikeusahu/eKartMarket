package com.example.ekartmarket;

import static android.widget.Toast.LENGTH_LONG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ekartmarket.Adapter.CartProductAdapter;
import com.example.ekartmarket.Model.CartModel;
import com.example.ekartmarket.Model.CategoryModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserCart extends AppCompatActivity {
    TextView cart_username,cart_useraddress,cart_usermobno,cart_shoppingamt,cart_dicount,cart_deliveryfee,cart_platformfee,cart_totalamt,cart_totalamtbottom;

    MaterialButton cart_btnorder;
     RecyclerView cart_recycler;
    String name="",useraddress="",mobno="",pincode="",houseno="",landmark="";

    int total =0,totalitems=0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_cart);

        cart_username = findViewById(R.id.cart_username);
        cart_useraddress = findViewById(R.id.cart_useraddress);
        cart_usermobno = findViewById(R.id.cart_usermobno);
        cart_shoppingamt = findViewById(R.id.cart_shoppingamt);
        cart_dicount = findViewById(R.id.cart_dicount);
        cart_deliveryfee = findViewById(R.id.cart_deliveryfee);
        cart_platformfee = findViewById(R.id.cart_platformfee);
        cart_totalamt = findViewById(R.id.cart_totalamt);
        cart_totalamtbottom = findViewById(R.id.cart_totalamtbottom);
        cart_btnorder = findViewById(R.id.cart_btnorder);
        cart_recycler = findViewById(R.id.cart_productrecycler);

//get detail of current login user


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
      // Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();
        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    name = snapshot.child("name").getValue() != null ? snapshot.child("name").getValue().toString() : "";
                    useraddress = snapshot.child("address").getValue() != null ? snapshot.child("address").getValue().toString() : "";
                    mobno = snapshot.child("mobileno").getValue() != null ? snapshot.child("mobileno").getValue().toString() : "";
                    pincode = snapshot.child("pincode").getValue() != null ? snapshot.child("pincode").getValue().toString() : "";
                    houseno = snapshot.child("houseno").getValue() != null ? snapshot.child("houseno").getValue().toString() : "";
                    landmark = snapshot.child("landmark").getValue() != null ? snapshot.child("landmark").getValue().toString() : "";
                    cart_username.setText("Welcome, " + name);
                    cart_useraddress.setText("Delivery Address : House No : " + houseno + ",Landmark : " + landmark + ", pincode : " + pincode);
                    cart_usermobno.setText("Contect No : " + mobno);


                    //Toast.makeText(getApplicationContext(),snapshot.child("name").getValue().toString(),LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "no data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "error on server ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


    //get all data of cartproduct
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://edge.techpile.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Call<CartModel> cart = api.getcartdetails(email);
        cart.enqueue(new Callback<CartModel>() {
            @Override
            public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                if (response.isSuccessful())
                {
                    CartModel data = response.body();
                    if (data!=null)
                    {
                        cart_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        CartProductAdapter adapter = new CartProductAdapter(getApplicationContext(),data.getCartItems());
                        cart_recycler.setAdapter(adapter);

                        cart_shoppingamt.setText("Rs."+data.getMrp()+"");
                        cart_dicount.setText("Rs."+data.getDiscountAmount()+"");
                        cart_totalamt.setText("Rs."+data.getTotalAmount()+"");
                        cart_totalamtbottom.setText("Rs."+data.getTotalAmount()+"");
                        cart_deliveryfee.setText("Rs.50");
                        cart_platformfee.setText("Rs.20");
                        total=data.getTotalAmount();
                        totalitems=data.getTotalItems();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Your cart is empty", LENGTH_LONG).show();
                        cart_btnorder.setVisibility(View.GONE);
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"error in api", LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CartModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"error in server", LENGTH_LONG).show();
            }
        });
        //place order
        cart_btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Order button clicked", LENGTH_LONG).show();
                Call<String>placeorder=
                        null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    placeorder = api.insertorder(name,mobno,houseno,landmark,useraddress,pincode,email,totalitems,total,"Pending", LocalDate.now().toString(), LocalDate.now().toString());
                }
                placeorder.enqueue(new Callback<String>() {
                   @Override
                   public void onResponse(Call<String> call, Response<String> response) {
                     if(response.isSuccessful())
                     {
                         String res = response.body();
                         Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                         Intent i = new Intent(UserCart.this, UserHistory.class);
                         startActivity(i);

                     }else {
                         Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_SHORT).show();
                     }
                   }

                   @Override
                   public void onFailure(Call<String> call, Throwable t) {
                       Toast.makeText(getApplicationContext(), "Error Occured. Please try again.", Toast.LENGTH_SHORT).show();
                   }
               });
            }
        });

    }
}