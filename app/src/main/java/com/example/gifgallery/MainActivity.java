package com.example.gifgallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        NavController navigationController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(bottomNavigationView, navigationController);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_UNLABELED);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}