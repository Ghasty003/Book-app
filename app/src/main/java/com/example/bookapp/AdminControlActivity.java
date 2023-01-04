package com.example.bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminControlActivity extends AppCompatActivity {

    private NavigationView navigationView;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control);

        navigationView = findViewById(R.id.navigationView);
        TextView textView = findViewById(R.id.test);

        textView.setText(new Users().getUsername());

        firebaseAuth = FirebaseAuth.getInstance();

        navigationListener();
    }

    private void navigationListener() {
        navigationView.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.logout:
                    firebaseAuth.signOut();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    break;
                case R.id.display_users:
                    startActivity(new Intent(this, DisplayUsersActivity.class));
                    break;
                case R.id.add:
                    startActivity(new Intent(this, AddNewBookActivity.class));
                    break;

            }

            return true;
        });
    }

}