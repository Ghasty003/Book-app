package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteBookActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DeleteBooksAdapter deleteBooksAdapter;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_book);

        recyclerView = findViewById(R.id.delete_book_recyclerView);

        firebaseFirestore = FirebaseFirestore.getInstance();

        getBooksFromDB();
    }

    private void getBooksFromDB() {
        try {
            Query query = FirebaseFirestore.getInstance().collection("books").orderBy("bookName", Query.Direction.ASCENDING);
            FirestoreRecyclerOptions<DeleteItems> options = new FirestoreRecyclerOptions.Builder<DeleteItems>().setQuery(query, DeleteItems.class).build();

            deleteBooksAdapter = new DeleteBooksAdapter(options, this);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(deleteBooksAdapter);
        } catch (Exception e) {
            Log.d("MY_APP", e.getLocalizedMessage());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        deleteBooksAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        deleteBooksAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        deleteBooksAdapter.notifyDataSetChanged();
    }
}