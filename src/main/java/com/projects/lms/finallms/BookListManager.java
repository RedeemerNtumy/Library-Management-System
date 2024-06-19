package com.projects.lms.finallms;

import java.util.ArrayList;
import java.util.List;

public class BookListManager {
    private List<Book> books;
    private BookDAO bookDAO;

    public BookListManager() {
        this.books = new ArrayList<>();
        this.bookDAO = new BookDAO();
        loadBooks();  // Initialize the book list by loading from the database
    }

    // Add a book to the list and the database
    public void addBook(Book book) {
        bookDAO.addBook(book);  // Add book to the database and retrieve generated ID
        books.add(book);        // Add book to the local list
    }

    // Remove a book from the list and the database
    public void removeBook(int bookID) {
        bookDAO.removeBook(bookID);  // Remove book from the database
        books.removeIf(b -> b.getBookID() == bookID);  // Remove book from the local list
    }

    // Update book availability in the list and the database
    public void updateBookAvailability(int bookID, boolean isAvailable) {
        bookDAO.updateBookAvailability(bookID, isAvailable);  // Update availability in the database
        for (Book book : books) {
            if (book.getBookID() == bookID) {
                book.setAvailable(isAvailable);  // Update availability in the local list
                break;
            }
        }
    }

    // Load all books from the database
    private void loadBooks() {
        books = bookDAO.getAllBooks();  // Assuming such a method exists in BookDAO
    }

    // Getter for the list of books
    public List<Book> getBooks() {
        return books;
    }
}
