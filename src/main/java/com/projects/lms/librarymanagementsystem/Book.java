package com.projects.lms.librarymanagementsystem;

public class Book {
    private int id;  // Assuming you decide to include an ID.
    private String title;
    private String author;
    private String isbn;
    private String shelfLocation;
    private String status;

    public Book(int id, String title, String author, String isbn, String shelfLocation, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.shelfLocation = shelfLocation;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    // Getter and setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and setter for author
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // Getter and setter for ISBN
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    // Getter and setter for shelf location
    public String getShelfLocation() {
        return shelfLocation;
    }

    public void setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
    }

    // Getter and setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the Book, typically for debugging purposes.
     *
     * @return A string representing the book details.
     */
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", shelfLocation='" + shelfLocation + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
