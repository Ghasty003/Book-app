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

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class UsersBookAdapter extends FirestoreRecyclerAdapter<BookUpload, UsersBookAdapter.UsersBookViewHolder> {

    Context context;

    public UsersBookAdapter(@NonNull FirestoreRecyclerOptions<BookUpload> options, Context context) {
        super(options);
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull UsersBookViewHolder holder, int position, @NonNull BookUpload book) {
        holder.authorName.setText(book.getAuthorName());
        holder.bookName.setText(book.getBookName());
        Picasso.with(context).load(book.getImageUri()).into(holder.imageView);
    }

    @NonNull
    @Override
    public UsersBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.usersbook_view, parent, false);
        return new UsersBookViewHolder(view);
    }

    class UsersBookViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView authorName, bookName;
        ImageButton imageButton;

        public UsersBookViewHolder(@NonNull View itemView) {
            super(itemView);

            imageButton = itemView.findViewById(R.id.addToCollection);
            imageView = itemView.findViewById(R.id.userBook_image_view);
            authorName = itemView.findViewById(R.id.userAuthor_text_view);
            bookName = itemView.findViewById(R.id.userBook_text_view);
        }
    }
}
