package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class DeleteBookActivity extends AppCompatActivity {

    private SearchView searchView;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_book);

        searchView = findViewById(R.id.search_view);

        firebaseFirestore = FirebaseFirestore.getInstance();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getBooksFromDB(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getBooksFromDB(String bookName) {
        firebaseFirestore.collection("books").whereEqualTo("bookName", bookName).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            for (QueryDocumentSnapshot q : task.getResult()) {
                if (q.exists()) {
                    Log.d("MY_APP", "doc exists");
                } else {
                    Log.d("MY_APP", "no such doc");
                }
            }
        });
    }
}