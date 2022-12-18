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

import com.example.promap.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{
    public static Menu menu;
    Fragment selectedFragment;
    Navigator navigator = new Navigator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}