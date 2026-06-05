package com.example.ekartmarket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLogin extends AppCompatActivity {




    MaterialButton btn_login;
TextView txt_gotoregister;
    TextView txt_forgotPass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_login);


        txt_gotoregister=findViewById(R.id.gotoregister);
        txt_forgotPass = findViewById(R.id.changepass);
        txt_gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(UserLogin.this,UserRegistration.class);
                startActivity(i);

            }
        });
        // 🔹 Forget Password Click Listener
        txt_forgotPass.setOnClickListener(v -> {
            TextInputEditText edt_emailid = findViewById(R.id.edt_email);
            String email = edt_emailid.getText().toString();

            if (email.isEmpty()) {
                Toast.makeText(UserLogin.this, "Please enter your email", Toast.LENGTH_LONG).show();
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(UserLogin.this, "Password reset email sent", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(UserLogin.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
        btn_login=findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText edt_emailid = findViewById(R.id.edt_email);
                TextInputEditText edt_password = findViewById(R.id.edt_password);

                String email = edt_emailid.getText().toString();
                String pass = edt_password.getText().toString();

                if (email.isEmpty()|| pass.isEmpty())
                {
                    Toast.makeText(UserLogin.this, " Please input email and password ", Toast.LENGTH_LONG).show();
                }
                else
                {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                               Intent i= new Intent(UserLogin.this,userdashboard.class);
                               startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(UserLogin.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
}