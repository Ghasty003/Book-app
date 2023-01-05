package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserDetailsChangeActivity extends AppCompatActivity {
    private View header;
    private ImageButton backBtn;
    private EditText newDetail;
    private Button changeRequest;
    private boolean usernameChangeMode = false;
    FirebaseUser firebaseUser;

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
            newDetail.setHint("Enter new password");
            changeRequest.setText("update username");
        }

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        backBtn.setOnClickListener(view -> finish());

        changeRequest.setOnClickListener(view -> {
            if (username != null || username.equals("")) {
                changeUserName();
            }
        });
    }

    private void changeUserName() {

        String newUserName = newDetail.getText().toString();

        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(newUserName)
                .build();

        firebaseUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Username update successful.", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}