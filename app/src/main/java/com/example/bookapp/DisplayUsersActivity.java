package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class DisplayUsersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_users);

        recyclerView = findViewById(R.id.users_recyclerView);

        View header = findViewById(R.id.header);
        ImageButton img = header.findViewById(R.id.back_btn);

        img.setOnClickListener(view -> {
            startActivity(new Intent(this, AdminControlActivity.class));
        });

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = Utility.getCollectionReferenceForUsers().orderBy("username", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Users> options = new FirestoreRecyclerOptions.Builder<Users>().setQuery(query, Users.class).build();

        usersAdapter = new UsersAdapter(options, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(usersAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        usersAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usersAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        usersAdapter.notifyDataSetChanged();
    }
}