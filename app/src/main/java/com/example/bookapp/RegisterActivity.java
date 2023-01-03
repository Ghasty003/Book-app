package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private TextView login;
    private EditText userNameEditText, passwordEditText, emailEditText;
    private ProgressBar progressBar;
    private Button registerBtn;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    UserProfileChangeRequest userProfileChangeRequest;
    Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login = findViewById(R.id.login);
        userNameEditText = findViewById(R.id.user_name);
        passwordEditText = findViewById(R.id.password);
        emailEditText = findViewById(R.id.email);
        progressBar = findViewById(R.id.progressBar);
        registerBtn = findViewById(R.id.btn);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        registerBtn.setOnClickListener(view -> createUser());

        login.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
    }

    private void createUser() {
        String emailText = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String userName = userNameEditText.getText().toString();

        boolean isValid = validateUserInput(emailText, password, userName);

        if (!isValid) {
            return;
        }

        showProgress(true);

        firebaseAuth.createUserWithEmailAndPassword(emailText, password).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);
                return;
            }

            map.put("Email", emailText);
            map.put("Password", password);
            map.put("username", userName);

            userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(userName).build();


            firebaseFirestore.collection("bookUsers").document(Objects.requireNonNull(firebaseUser.getUid())).set(map).addOnCompleteListener(userTask -> {
                if (!userTask.isSuccessful()) {
                    Toast.makeText(this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    showProgress(false);
                    return;
                }

                firebaseUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(t -> {
                    if (!t.isSuccessful()) {
                        Log.d("MY_APP", t.getException().getLocalizedMessage());
                        return;
                    }

                    Log.d("MY_APP", "Update successful");
                });

                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            });
        });
    }

    private boolean validateUserInput(String email, String password, String userName) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Invalid email address");
            return false;
        }

        if (!(password.length() > 6)) {
            passwordEditText.setError("Password must be more than six characters");
            return false;
        }

        if (userName.equals("")) {
            userNameEditText.setError("Username cannot be empty");
            return false;
        }

        return true;
    }

    private void showProgress(boolean show) {
        if (!show) {
            registerBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }

        registerBtn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }
}