package com.example.ekartmarket.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ekartmarket.CategoryWiseProduct;
import com.example.ekartmarket.Model.CategoryModel;
import com.example.ekartmarket.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<CategoryModel> categoryList;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_category_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoryModel category = categoryList.get(position);
        holder.categoryName.setText(category.getCatName());
        Glide.with(context).load(category.getPicture()).into(holder.categoryIcon);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context, CategoryWiseProduct.class);
                i.setFlags(i.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("catid",categoryList.get(position).getCatId());
                i.putExtra("catname",categoryList.get(position).getCatName());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryIcon;
        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.sample_caticon);
            categoryName = itemView.findViewById(R.id.sample_catname);
        }
    }
}