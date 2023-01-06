package com.example.bookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DeleteBooksAdapter extends RecyclerView.Adapter<DeleteBooksAdapter.DeleteBookViewHolder> {

    Context context;
    List<DeleteItems> deleteItems;

    public DeleteBooksAdapter(Context context, List<DeleteItems> deleteItems) {
        this.context = context;
        this.deleteItems = deleteItems;
    }

    @NonNull
    @Override
    public DeleteBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.delete_books_view, parent, false);
        return new DeleteBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteBookViewHolder holder, int position) {
        holder.authorName.setText(deleteItems.get(position).getAuthorName());
        holder.bookName.setText(deleteItems.get(position).getBookName());
        Picasso.with(context).load(deleteItems.get(position).getBookImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return deleteItems.size();
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
