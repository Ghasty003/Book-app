package com.example.bookapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookapp.R;
import com.example.bookapp.UserCollection;
import com.example.bookapp.UserCollectionAdapter;
import com.example.bookapp.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class BooksFragment extends Fragment {

    RecyclerView recyclerView;
    UserCollectionAdapter userCollectionAdapter;
    CollectionReference collectionReference;

    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        collectionReference = FirebaseFirestore.getInstance().collection("usersCollection").document(user.getUid()).collection("books");

        recyclerView = view.findViewById(R.id.userCollectionRecyclerView);

        Query query = collectionReference.orderBy("bookName", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<UserCollection> options = new FirestoreRecyclerOptions.Builder<UserCollection>().setQuery(query, UserCollection.class).build();

        userCollectionAdapter = new UserCollectionAdapter(options, getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(userCollectionAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_books, container, false);
    }
}