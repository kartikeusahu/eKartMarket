package com.example.ekartmarket;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity {

    TextView edt_userid , edt_password;
    MaterialButton btn_adminlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_login);

        edt_userid=findViewById(R.id.edt_userid);
        edt_password=findViewById(R.id.edt_password);

        btn_adminlogin=findViewById(R.id.btn_adminlogin);

        btn_adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid= edt_userid.getText().toString();
                String password = edt_password.getText().toString();

                FirebaseAuth.getInstance().signInWithEmailAndPassword(userid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                           Intent i= new Intent(AdminLogin.this,AdminDashboard.class);
                           startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(AdminLogin.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null && FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("kartikey@gmail.com"))
        {
           Intent i= new Intent(AdminLogin.this,AdminDashboard.class);
           startActivity(i);
        }
    }
}