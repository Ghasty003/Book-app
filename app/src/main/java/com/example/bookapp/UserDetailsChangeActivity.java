package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UserDetailsChangeActivity extends AppCompatActivity {
    private View header;
    private ImageButton backBtn;
    private EditText newDetail;
    private Button changeRequest;
    private boolean usernameChangeMode = false;

    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_change);

        newDetail = findViewById(R.id.new_detail);
        changeRequest = findViewById(R.id.update_profile);
        header = findViewById(R.id.header);
        backBtn = header.findViewById(R.id.back_btn);

        String username = getIntent().getStringExtra("username");
        if (username != null || !username.equals("")) {
            usernameChangeMode = true;
        }

        if (usernameChangeMode) {
            if (username != null || !username.equals("")) {
                newDetail.setHint("Enter new password");
                changeRequest.setText("update username");

                changeRequest.setOnClickListener(view -> {
                    if (username != null || username.equals("")) {
                        changeUserName();
                    }
                });
            }
        }


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        backBtn.setOnClickListener(view -> finish());
    }

    private void changeUserName() {

        String newUserName = newDetail.getText().toString();

        if (newUserName.equals("")) {
            Toast.makeText(this, "Field can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(newUserName)
                .build();

        firebaseUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseFirestore.collection("bookUsers").whereEqualTo("email", firebaseUser.getEmail()).get().addOnCompleteListener(task1 -> {

                if (!task1.isSuccessful()) {
                    Toast.makeText(this, task1.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                for (QueryDocumentSnapshot q : task1.getResult()) {
                    if (q.exists()) {
                        DocumentReference documentReference = q.getReference();
                        documentReference.update("username", newUserName).addOnCompleteListener(innerTask -> {
                            Toast.makeText(this, "Username update successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
                            finish();
                        });
                    } else {
                        Toast.makeText(this, "No such user", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}