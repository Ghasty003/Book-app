package com.example.bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AdminControlActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton openDrawer;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    BooksAdapter booksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control);

        drawerLayout = findViewById(R.id.drawer_menu);
        navigationView = findViewById(R.id.navigationView);
        recyclerView = findViewById(R.id.recyclerView);
        openDrawer = findViewById(R.id.image_btn);

        openDrawer.setOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        firebaseAuth = FirebaseAuth.getInstance();

        displayBooks();

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
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.add:
                    startActivity(new Intent(this, AddNewBookActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.display:
                    startActivity(new Intent(this, AdminControlActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
            }

            return true;
        });
    }

    private void displayBooks() {
        Query query = Utility.getCollectionReferenceForBooks().orderBy("bookName", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<BookUpload> options = new FirestoreRecyclerOptions.Builder<BookUpload>().setQuery(query, BookUpload.class).build();

        booksAdapter = new BooksAdapter(options, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(booksAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        booksAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        booksAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        booksAdapter.notifyDataSetChanged();
    }

}