package com.example.bookapp;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Utility {

    static CollectionReference getCollectionReferenceForUsers() {
        return FirebaseFirestore.getInstance().collection("bookUsers");
    }
}
