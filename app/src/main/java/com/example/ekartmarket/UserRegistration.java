package com.example.ekartmarket;

import static android.widget.Toast.LENGTH_LONG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ekartmarket.UserLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserRegistration extends AppCompatActivity {

    MaterialButton btn_register;

    TextInputEditText edt_name, edt_mobno, edt_email, edt_houseno, edt_address, edt_landmark, edt_pincode, edt_password;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_registration);

        //get reference of all element;
        edt_name = findViewById(R.id.edt_name);
        edt_address = findViewById(R.id.edt_address);
        edt_email = findViewById(R.id.edt_email);
        edt_houseno = findViewById(R.id.edt_houseno);
        edt_landmark = findViewById(R.id.edt_landmark);
        edt_mobno = findViewById(R.id.edt_mobno);
        edt_password = findViewById(R.id.edt_pass);
        edt_pincode = findViewById(R.id.edt_pin);
        btn_register = findViewById(R.id.btn_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //click event of btn register
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_name.getText().toString();
                String address = edt_address.getText().toString();
                String email = edt_email.getText().toString();
                String houseno = edt_houseno.getText().toString();
                String landmark = edt_landmark.getText().toString();
                String mobno = edt_mobno.getText().toString();
                String password = edt_password.getText().toString();
                String pincode = edt_pincode.getText().toString();
                HashMap map = new HashMap();
                map.put("name", name);
                map.put("address", address);
                map.put("email", email);
                map.put("houseno", houseno);
                map.put("landmark", landmark);
                map.put("mobileno", mobno);
                map.put("password", password);
                map.put("pincode", pincode);

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            FirebaseDatabase.getInstance().getReference().child("Users").child(uid).setValue(map).addOnCompleteListener(
                                    new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(getApplicationContext(),"Registration Successful",LENGTH_LONG).show();
                                                Intent i=new Intent(getApplicationContext(), UserLogin.class);
                                                startActivity(i);
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),LENGTH_LONG).show();
                                            }
                                        }
                                    });

                        }
                        else {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),LENGTH_LONG).show();
                        }
                    }
                });

            }
 });

}
}
