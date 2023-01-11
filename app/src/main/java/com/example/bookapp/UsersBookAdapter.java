package com.example.bookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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

        holder.imageButton.setOnClickListener(view -> {
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.imageButton.setVisibility(View.GONE);

            UserCollection collection = new UserCollection();
            collection.setAuthorName(book.authorName);
            collection.setBookName(book.bookName);
            collection.setImageUri(book.getImageUri());

            Utility.getCollectionReferenceForUsersBook().whereEqualTo("bookName", collection.getBookName()).whereEqualTo("authorName", collection.getAuthorName()).get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(context, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                for (DocumentSnapshot document : task.getResult()) {
                    if (document.exists()) {
                        Toast.makeText(context, "Book already exits in your collection", Toast.LENGTH_SHORT).show();
                        holder.progressBar.setVisibility(View.GONE);
                        holder.imageButton.setVisibility(View.VISIBLE);
                        return;
                    }
                }

                Utility.getCollectionReferenceForUsersBook().document().set(collection).addOnCompleteListener(task1 -> {
                    if (!task1.isSuccessful()) {
                        Toast.makeText(context, task1.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(context, "Book added to your collection successfully", Toast.LENGTH_SHORT).show();
                    holder.progressBar.setVisibility(View.GONE);
                    holder.imageButton.setVisibility(View.VISIBLE);
                });
            });

        });
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
        ProgressBar progressBar;

        public UsersBookViewHolder(@NonNull View itemView) {
            super(itemView);

            imageButton = itemView.findViewById(R.id.addToCollection);
            imageView = itemView.findViewById(R.id.userBook_image_view);
            authorName = itemView.findViewById(R.id.userAuthor_text_view);
            bookName = itemView.findViewById(R.id.userBook_text_view);
            progressBar = itemView.findViewById(R.id.progress_Bar);
        }
    }
}
