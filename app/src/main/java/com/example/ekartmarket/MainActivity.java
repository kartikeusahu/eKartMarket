package com.example.ekartmarket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    MaterialButton btn_userlogin, btn_adminlogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //get reference of elements
        btn_userlogin=findViewById(R.id.btn_userlogin);
        btn_adminlogin=findViewById(R.id.btn_adminlogin);

        //click event of button
        btn_userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,UserLogin.class);
                startActivity(i);
            }
        });
        //end click event of userlogin
        //start click event of adminlogin

        btn_adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,AdminLogin.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //if a user is alredy loged in , redirect to dashboard
        if(FirebaseAuth.getInstance().getCurrentUser()!=null && !FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("kartikey@gmail.com"))
        {
            Intent i=new Intent(MainActivity.this, userdashboard.class);
            startActivity(i);
        }
        else if (FirebaseAuth.getInstance().getCurrentUser()!=null && FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("kartikey@gmail.com"))
        {
            Intent i = new Intent(MainActivity.this, AdminDashboard.class);
            startActivity(i);
        }
    }
}