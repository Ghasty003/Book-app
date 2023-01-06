package com.example.bookapp;

import android.app.AlertDialog;
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

    Context context;

    public DeleteBooksAdapter(@NonNull FirestoreRecyclerOptions<DeleteItems> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull DeleteBookViewHolder holder, int position, @NonNull DeleteItems deleteItems) {
        holder.authorName.setText(deleteItems.getAuthorName());
        holder.bookName.setText(deleteItems.getBookName());
        String imageUri = this.getSnapshots().getSnapshot(position).getString("imageUri");
        Picasso.with(context).load(imageUri).into(holder.imageView);

        String docId = this.getSnapshots().getSnapshot(position).getId();

        holder.imageButton.setOnClickListener(view -> {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Delete book");
            alertDialog.setMessage("Do you want to delete item?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", ((dialogInterface, i) -> {
                FirebaseFirestore.getInstance().collection("books").document(docId).delete().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(context, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(context, "deleted successfully", Toast.LENGTH_SHORT).show();
                });
            }));

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", ((dialogInterface, i) -> {
                alertDialog.dismiss();
            }));

            alertDialog.show();
        });
    }

    @NonNull
    @Override
    public DeleteBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_books_view, parent, false);
        return new DeleteBookViewHolder(view);
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
