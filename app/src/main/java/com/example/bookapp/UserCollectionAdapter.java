package com.example.bookapp;

import android.app.AlertDialog;
import android.content.Context;
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
import com.squareup.picasso.Picasso;

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

        String docId = this.getSnapshots().getSnapshot(position).getId();

        holder.imageButton.setOnClickListener(view -> {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Delete item from collection");
            alertDialog.setMessage("Are you sure you want to delete? Action is irreversible");
            alertDialog.setButton(alertDialog.BUTTON_POSITIVE, "Yes", ((dialogInterface, i) -> {
                Utility.getCollectionReferenceForUsersBook().document(docId).delete().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(context, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(context, "Item removed from collection successfully.", Toast.LENGTH_SHORT).show();
                });
            }));
            alertDialog.setButton(alertDialog.BUTTON_NEGATIVE, "Cancel", ((dialogInterface, i) -> {
                dialogInterface.dismiss();
            }));
            alertDialog.show();
        });
    }

    @NonNull
    @Override
    public UserCollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_collection_view, parent, false);
        return new UserCollectionViewHolder(view);
    }

    static class UserCollectionViewHolder extends RecyclerView.ViewHolder {

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
