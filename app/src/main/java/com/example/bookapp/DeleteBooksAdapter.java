package com.example.bookapp;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeleteBooksAdapter {

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
