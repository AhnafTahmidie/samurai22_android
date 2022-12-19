package com.example.promap.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.example.promap.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity{
    public static Menu menu;
    Fragment selectedFragment;
    Navigator navigator = new Navigator();
    Button btnLogOut;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogOut = findViewById(R.id.btnLogout);
        mAuth = FirebaseAuth.getInstance();

        btnLogOut.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);
        getSupportFragmentManager().beginTransaction().replace(R.id.id_fragment_controller,
                new HomeFragment()).commit();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeFragment:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.mapFragment:
                    selectedFragment = new MapFragment();
                    break;
                default:
                    selectedFragment = null;
                    break;
            }
            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.id_fragment_controller,
                    selectedFragment).commit();
            return true;
        });
    }

    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}