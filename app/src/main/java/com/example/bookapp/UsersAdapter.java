package com.example.bookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class UsersAdapter extends FirestoreRecyclerAdapter<Users, UsersAdapter.UsersViewHolder> {

    Context context;

    public UsersAdapter(@NonNull FirestoreRecyclerOptions<Users> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users users) {
        holder.email.setText(users.getEmail());
        holder.username.setText(users.getUsername());
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_view, parent, false);
        return new UsersViewHolder(view);
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView email, username;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.users_email);
            username = itemView.findViewById(R.id.users_username);
        }
    }
}
