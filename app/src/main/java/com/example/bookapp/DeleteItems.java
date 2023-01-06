package com.example.bookapp;


public class DeleteItems {
    String authorName, bookName, bookImage;

    public DeleteItems(String authorName, String bookName, String bookImage) {
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

    public String getBookImage() {
        return bookImage;
    }
}
