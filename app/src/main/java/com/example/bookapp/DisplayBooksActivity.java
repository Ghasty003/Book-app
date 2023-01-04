package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class DisplayBooksActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BooksAdapter booksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_books);

        recyclerView = findViewById(R.id.recyclerView);

        displayBooks();
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