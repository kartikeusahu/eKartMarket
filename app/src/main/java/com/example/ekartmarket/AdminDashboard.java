package com.example.ekartmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboard extends AppCompatActivity {
    DrawerLayout admindrawer_layout;

    Toolbar admin_toolbat;

    NavigationView admin_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);

        //get reference of elements
        admindrawer_layout=findViewById(R.id.admindrawer_layout);
        admin_toolbat = findViewById(R.id.admin_toolbar);
        admin_nav=findViewById(R.id.admin_nav);

        //set toolbar

        setSupportActionBar(admin_toolbat);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,admindrawer_layout,admin_toolbat,R.string.Welcome_msg,R.string.Welcome_msg);
        admindrawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        //set onclickevent of all menu items of nevigation view
        admin_nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId()==R.id.menu_adashboard)
                {
                    Intent i= new Intent(getApplicationContext(),AdminDashboard.class);
                    startActivity(i);
                } else if (menuItem.getItemId()==R.id.menu_acategory) {
                    Intent i= new Intent(getApplicationContext(),AdminAddCategory.class);
                    startActivity(i);
                }else if (menuItem.getItemId()==R.id.menu_aproduct) {
                    Intent i= new Intent(getApplicationContext(),AdminAddProduct.class);
                    startActivity(i);
                }else if (menuItem.getItemId()==R.id.menu_aproductlist) {
                    Intent i= new Intent(getApplicationContext(),AdminProductList.class);
                    startActivity(i);
                }else if (menuItem.getItemId()==R.id.menu_auserlist) {
                    Intent i= new Intent(getApplicationContext(),AdminUserList.class);
                    startActivity(i);
                }else if (menuItem.getItemId()==R.id.menu_aorders) {
                    Intent i= new Intent(getApplicationContext(),AdminAllOrder.class);
                    startActivity(i);
                }else if (menuItem.getItemId()==R.id.menu_alogout) {
                   FirebaseAuth.getInstance().signOut();
                   Intent i= new Intent(getApplicationContext(),AdminLogin.class);
                   startActivity(i);
                }

                return true;
            }
        });

    }

    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null || !FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("kartikey@gmail.com"))
        {
            Intent i= new Intent(AdminDashboard.this,AdminLogin.class);
            startActivity(i);
        }
    }
}