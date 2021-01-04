package com.example.holoubkoviknihy.model;

public class Book {
    private String Name;
    private String Author;
    private String Description;
    private String Category;

    public Book(String Name, String Author, String Description, String Category) {
        this.Name = Name;
        this.Author = Author;
        this.Description = Description;
        this.Category = Category;
    }


    public String getTitle() {
        return Name;
    }

    public String getAuthors() {
        return Author;
    }

    public String getDescription() {
        return Description;
    }

    public String getCategories() {
        return Category;
    }

}
