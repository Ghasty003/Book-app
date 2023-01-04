package com.example.bookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class BooksAdapter extends FirestoreRecyclerAdapter<BookUpload, BooksAdapter.BooksViewHolder> {

    Context context;

    public BooksAdapter(@NonNull FirestoreRecyclerOptions<BookUpload> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull BooksViewHolder holder, int position, @NonNull BookUpload bookUpload) {
        holder.authorView.setText(bookUpload.getAuthorName());
        holder.bookView.setText(bookUpload.getBookName());
        Picasso.with(context).load(bookUpload.getImageUri()).into(holder.imageView);
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_view, parent, false);
        return new BooksViewHolder(view);
    }

    class BooksViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView authorView, bookView;

        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.book_image_view);
            authorView = itemView.findViewById(R.id.author_text_view);
            bookView = itemView.findViewById(R.id.book_text_view);
        }
    }
}
