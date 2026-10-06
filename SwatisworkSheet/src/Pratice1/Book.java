package Pratice1;

public class Book {
    // private fields — only this class can access them directly (encapsulation)
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;

    // constructor — runs when you create a new Book object
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true; // new books start as available
    }

    // getters — let other classes READ the private fields
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // setter — only for things that should change after creation
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    // toString — makes printing the object readable
    @Override
    public String toString() {
        return title + " by " + author + " (ISBN: " + isbn + ") - " +
               (isAvailable ? "Available" : "Borrowed");
    }
}
