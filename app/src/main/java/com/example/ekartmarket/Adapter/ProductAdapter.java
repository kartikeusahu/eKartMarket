package com.example.ekartmarket.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ekartmarket.ApiService;
import com.example.ekartmarket.Model.ProductModel;
import com.example.ekartmarket.R;
import com.example.ekartmarket.UserProductDetails;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    public ProductAdapter()
    {

    }
    Context con;
    ArrayList<ProductModel>data;
    public ProductAdapter(Context con,ArrayList<ProductModel>data)
    {
        this.con=con;
        this.data=data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(con).inflate(R.layout.sample_product_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.sample_productname.setText(data.get(position).getName());
        holder.sample_productmrp.setText("₹"+data.get(position).getMRP());
        holder.sample_productsalerate.setText("₹"+data.get(position).getSaleRate()+"/-");
        holder.sample_productsize.setText(data.get(position).getPack_size());
        Glide.with(con).load(data.get(position).getPicture()).into(holder.sample_productimg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(con, UserProductDetails.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id",data.get(position).getId());
                con.startActivity(i);
            }
        });
        holder.sample_productplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(holder.sample_productquantity.getText().toString());
                q=q+1;
                holder.sample_productquantity.setText(q+"");
                String emailid = FirebaseAuth.getInstance().getCurrentUser()!=null?FirebaseAuth.getInstance().getCurrentUser().getEmail():"";
                if(emailid!=null && !emailid.isEmpty())
                {
                    AddToCart(emailid,data.get(position).getId(),q);
                }
                else {
                    Toast.makeText(con,"Error in adding cart",Toast.LENGTH_LONG).show();
                }

            }
        });
        holder.sample_productminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(holder.sample_productquantity.getText().toString());
                if (q>0) {
                    q = q - 1;
                    holder.sample_productquantity.setText(q + "");


                    String emailid = FirebaseAuth.getInstance().getCurrentUser()!=null?FirebaseAuth.getInstance().getCurrentUser().getEmail():"";
                    if(emailid!=null && !emailid.isEmpty())
                    {
                        AddToCart(emailid,data.get(position).getId(),q);
                    }
                    else {
                        Toast.makeText(con,"Error in adding cart",Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(con,"Quantity cannot be less than 0",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void AddToCart(String emailid,int pid,int unit)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://edge.techpile.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);

        Call<String> addtocart = api.addtocart(emailid,pid,unit);
            addtocart.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful())
                    {
                        String message = response.body();
                       // Toast.makeText(con,message,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                       // Toast.makeText(con,"error",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                   // Toast.makeText(con,"failure on server",Toast.LENGTH_LONG).show();
                }
            });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView sample_productimg;
        TextView sample_productname, sample_productmrp,sample_productsalerate,sample_productsize ;
        TextView sample_productquantity,sample_productminus,sample_productplus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sample_productimg=itemView.findViewById(R.id.sample_product_img);
            sample_productname=itemView.findViewById(R.id.sample_product_name);
            sample_productsalerate=itemView.findViewById(R.id.sample_product_salerate);
            sample_productmrp=itemView.findViewById(R.id.sample_product_mrp);
            sample_productsize=itemView.findViewById(R.id.sample_productsize);


            sample_productquantity=itemView.findViewById(R.id.sample_productquantity);
            sample_productminus=itemView.findViewById(R.id.sample_productminus);
            sample_productplus=itemView.findViewById(R.id.sample_productplus);
        }
    }

}
