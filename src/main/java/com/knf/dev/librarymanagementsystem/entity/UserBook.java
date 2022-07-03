package com.knf.dev.librarymanagementsystem.entity;

public class UserBook {

    private int id;
    private String book_name;
    private String email;

    public UserBook(String book_name, String email) {
        this.book_name = book_name;
        this.email = email;
    }

    public UserBook() {
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
