package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null && user.getEmail().equals("admin@dev.com")) {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashScreenActivity.this, AdminControlActivity.class));
                finish();
            }, 1000);
        } else if (user != null) {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }, 1000);
        } else {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();
            }, 1000);
        }
    }
}