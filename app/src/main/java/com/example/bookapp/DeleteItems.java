package com.example.bookapp;

import android.widget.ImageView;

public class DeleteItems {
    String authorName, bookName;
    ImageView bookImage;

    public DeleteItems(String authorName, String bookName, ImageView bookImage) {
        this.authorName = authorName;
        this.bookName = bookName;
        this.bookImage = bookImage;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getBookName() {
        return bookName;
    }

    public ImageView getBookImage() {
        return bookImage;
    }
}
