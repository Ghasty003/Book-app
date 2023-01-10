package com.example.bookapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Utility {

    static CollectionReference getCollectionReferenceForUsers() {
        return FirebaseFirestore.getInstance().collection("bookUsers");
    }

    static CollectionReference getCollectionReferenceForBooks() {
        return FirebaseFirestore.getInstance().collection("books");
    }

    static CollectionReference getCollectionReferenceForUsersBook() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("usersCollection").document(user.getUid()).collection("books");
    }
}
