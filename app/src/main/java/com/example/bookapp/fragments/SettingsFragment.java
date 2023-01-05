package com.example.bookapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookapp.LoginActivity;
import com.example.bookapp.R;
import com.example.bookapp.UserDetailsChangeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SettingsFragment extends Fragment {

    private TextView logout, username, deleteAccount, updateUsername;
    FirebaseUser user;
    FirebaseFirestore firebaseFirestore;


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logout = view.findViewById(R.id.logout);
        username = view.findViewById(R.id.user_name);
        deleteAccount = view.findViewById(R.id.delete_account);
        updateUsername = view.findViewById(R.id.update_username);

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        username.setText("Welcome back, " + user.getDisplayName());

        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        });

        deleteAccount.setOnClickListener(v -> {
           deleteUserAccount();
        });

        updateUsername.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), UserDetailsChangeActivity.class);
            intent.putExtra("username", "username");
            startActivity(intent);
        });
    }

    public void deleteUserAccount() {

        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Delete account");
        alertDialog.setMessage("This action is irreversible");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", ((dialogInterface, i) -> {
            user.delete().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(getContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                    return;
                }

                firebaseFirestore.collection("bookUsers").whereEqualTo("email", user.getEmail()).get().addOnCompleteListener(task1 -> {
                    if (!task1.isSuccessful()) {
                        Toast.makeText(getContext(), task1.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                        return;
                    }

                    QuerySnapshot querySnapshot = task1.getResult();

                    for (DocumentSnapshot q : querySnapshot) {
                        if (q.exists()) {
                            q.getReference().delete().addOnCompleteListener(innerTask -> {
                                if (!innerTask.isSuccessful()) {
                                    Toast.makeText(getContext(), innerTask.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    dialogInterface.dismiss();
                                    return;
                                }

                                Toast.makeText(getContext(), "Account deleted successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getContext(), LoginActivity.class));
                                getActivity().finish();
                            });
                        } else {
                            Toast.makeText(getContext(), "No such user.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            });
        }));

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", ((dialogInterface, i) -> {
            dialogInterface.dismiss();
        }));

        alertDialog.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}