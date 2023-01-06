package com.example.bookapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class DeleteBooksAdapter extends FirestoreRecyclerAdapter<DeleteItems, DeleteBooksAdapter.DeleteBookViewHolder> {


    public DeleteBooksAdapter(@NonNull FirestoreRecyclerOptions<DeleteItems> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DeleteBookViewHolder holder, int position, @NonNull DeleteItems model) {

    }

    @NonNull
    @Override
    public DeleteBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    class DeleteBookViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageButton imageButton;
        TextView authorName, bookName;

        public DeleteBookViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.delete_book_image_view);
            imageButton = itemView.findViewById(R.id.delete_item_icon);
            authorName = itemView.findViewById(R.id.delete_author_text_view);
            bookName = itemView.findViewById(R.id.delete_book_text_view);
        }
    }
}
