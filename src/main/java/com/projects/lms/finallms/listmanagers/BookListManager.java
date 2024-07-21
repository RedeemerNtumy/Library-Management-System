package com.projects.lms.finallms.listmanagers;

import com.projects.lms.finallms.dao.BookDAO;
import com.projects.lms.finallms.models.Book;

import java.util.LinkedList;

public class BookListManager {
    private LinkedList<Book> books;
    public BookDAO bookDAO;

    public BookListManager() {
        this.books = new LinkedList<>();
        this.bookDAO = new BookDAO();
        loadBooks();  // Initialize the book list by loading from the database
    }

    public void addBook(Book book) {
        bookDAO.addBook(book);
        books.add(book);
    }

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
    public LinkedList<Book> getBooks() {
        return books;
    }
}
