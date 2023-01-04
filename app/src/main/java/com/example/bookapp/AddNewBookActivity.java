package com.example.bookapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class AddNewBookActivity extends AppCompatActivity {

    private EditText authorName, bookName;
    private Button addBook;
    private ImageView imageView;
    private TextView chooseImg;
    private final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    StorageReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);

        authorName = findViewById(R.id.author_name);
        bookName = findViewById(R.id.book_name);
        imageView = findViewById(R.id.book_img);
        addBook = findViewById(R.id.add_book);
        chooseImg = findViewById(R.id.choose_img);

        reference = FirebaseStorage.getInstance().getReference("books");

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
            Picasso.with(this).load(imageUri).into(imageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void addBookToFirestore() {
        String book_name = bookName.getText().toString();
        String author_name = authorName.getText().toString();

        BookUpload bookUpload = new BookUpload();

        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
        fileRef.putFile(imageUri).continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }

            return fileRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {

            Uri downloadUri = task.getResult();

            bookUpload.setBookName(book_name);
            bookUpload.setAuthorName(author_name);
            bookUpload.setImageUri(downloadUri.toString());

            DocumentReference documentReference = Utility.getCollectionReferenceForBooks().document();

            documentReference.set(bookUpload).addOnCompleteListener(task1 -> {
               if (!task1.isSuccessful()) {
                   Toast.makeText(this, task1.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                   return;
               }

                Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show();
               authorName.setText("");
               bookName.setText("");
            });
        });
    }
}