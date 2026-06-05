package com.example.ekartmarket.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ekartmarket.Model.CartProductModel;
import com.example.ekartmarket.R;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {

    Context con;
    ArrayList<CartProductModel>data;
    public  CartProductAdapter(Context con, ArrayList<CartProductModel>data){
        this.con=con;
        this.data=data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View v = LayoutInflater.from(con).inflate(R.layout.sample_cartproductlayout,parent,false);
     return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.cart_productname.setText(data.get(position).getProductName());
        holder.cart_productprice.setText("Price :" + data.get(position).getSaleRate());
        holder.cart_productquantity.setText("Quantity :" + data.get(position).getUnit());

        Glide.with(con).load(data.get(position).getPicture()).into(holder.cart_productimage);
        holder.cart_productdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailid = FirebaseAuth.getInstance().getCurrentUser()!=null?FirebaseAuth.getInstance().getCurrentUser().getEmail():"";
                if(emailid!=null && !emailid.isEmpty())
                {
                   ProductAdapter p= new ProductAdapter();
                    p.AddToCart(emailid,data.get(position).getPid(),0);
                    data.remove(position);
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(con,"Error in adding cart",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cart_productname,cart_productprice,cart_productquantity;
        ImageView cart_productimage,cart_productdelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_productname=itemView.findViewById(R.id.samplecart_productname);
            cart_productprice=itemView.findViewById(R.id.samplecart_product_salerate);
            cart_productquantity=itemView.findViewById(R.id.samplecart_product_quantity);
            cart_productimage=itemView.findViewById(R.id.samplecart_productimage);
            cart_productdelete=itemView.findViewById(R.id.samplecart_productdelete);

        }
    }
}
