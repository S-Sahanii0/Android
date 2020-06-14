package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavBar_Food extends AppCompatActivity {
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar__food);

        bottomNav = findViewById(R.id.nav_bottom_id);
        bottomNav.setOnNavigationItemSelectedListener(navListner);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_id, new HomeNav()).commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment displayedFrag = null;

                    switch (menuItem.getItemId()) {
                        case R.id.home_id:
                            displayedFrag = new HomeNav();
                            break;

                        case R.id.order_id:
                            displayedFrag = new OrderNav();
                            break;

                        case R.id.cart_id:
                            displayedFrag = new CartNav();
                            break;

                        case R.id.profile_id:
                            displayedFrag = new ProfileNav();
                            break;
                    }

                    //for displaying frag
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_id, displayedFrag).commit();
                    return true;
                }
            };


}
