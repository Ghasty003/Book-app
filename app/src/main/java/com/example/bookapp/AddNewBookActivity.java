package com.example.bookapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddNewBookActivity extends AppCompatActivity {

    private EditText authorName, bookName;
    private Button addBook;
    private ImageView imageView;
    private TextView chooseImg;
    private final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);

        authorName = findViewById(R.id.author_name);
        bookName = findViewById(R.id.book_name);
        imageView = findViewById(R.id.book_img);
        addBook = findViewById(R.id.add_book);
        chooseImg = findViewById(R.id.choose_img);

        chooseImg.setOnClickListener(v -> chooseImage());

        addBook.setOnClickListener(view -> addBookToFirestore());
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setAction(intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data.getData() != null && data != null) {
            imageUri = data.getData();
        }
    }

    private void addBookToFirestore() {

    }
}