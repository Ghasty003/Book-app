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

import org.w3c.dom.Text;

public class UserCollectionAdapter extends FirestoreRecyclerAdapter<UserCollection, UserCollectionAdapter.UserCollectionViewHolder> {

    Context context;

    public UserCollectionAdapter(@NonNull FirestoreRecyclerOptions<UserCollection> options, Context context) {
        super(options);
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull UserCollectionViewHolder holder, int position, @NonNull UserCollection collection) {
        holder.authorName.setText(collection.getAuthorName());
        holder.bookName.setText(collection.getBookName());
        Picasso.with(context).load(collection.getImageUri()).into(holder.imageView);
    }

    @NonNull
    @Override
    public UserCollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_collection_view, parent, false);
        return new UserCollectionViewHolder(view);
    }

    class UserCollectionViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageButton imageButton;
        TextView authorName, bookName;

        public UserCollectionViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.user_collection_image_view);
            imageButton = itemView.findViewById(R.id.removeFromCollection);
            authorName = itemView.findViewById(R.id.user_collection_author_text_view);
            bookName = itemView.findViewById(R.id.user_collection_book_text_view);
        }
    }
}
