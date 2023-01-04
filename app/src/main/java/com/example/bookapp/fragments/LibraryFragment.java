package com.example.bookapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookapp.BookUpload;
import com.example.bookapp.BooksAdapter;
import com.example.bookapp.R;
import com.example.bookapp.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class LibraryFragment extends Fragment {

    RecyclerView recyclerView;
    BooksAdapter booksAdapter;

    public LibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.library_recycler_view);

        Query query = FirebaseFirestore.getInstance().collection("books").orderBy("bookName", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<BookUpload> options = new FirestoreRecyclerOptions.Builder<BookUpload>().setQuery(query, BookUpload.class).build();
        booksAdapter = new BooksAdapter(options, getActivity());

        recyclerView.setAdapter(booksAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        booksAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        booksAdapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        booksAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }
}