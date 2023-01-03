package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView register;
    private EditText emailEditText, passwordEditText;
    private Button loginBtn;
    private ProgressBar progressBar;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        loginBtn = findViewById(R.id.btn);

        firebaseAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(view -> loginUser());

        register.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.equals("") || password.equals("")) {
            return;
        }

        showProgress(true);

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);
                return;
            }

            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

            if (email.equals("admin@dev.com")) {
                startActivity(new Intent(this, AdminControlActivity.class));
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }

            finish();
        });
    }

    private void showProgress(boolean show) {
        if (!show) {
            loginBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }

        loginBtn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }
}