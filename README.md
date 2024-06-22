# Library Management System (LMS)

## Project Overview

The Library Management System (LMS) is designed to manage the operations of a library, including book management, patron management, and transaction processing for borrowing and returning books. The intended audience is the librarian. The system will be built using Java and will utilize a MySQL database for data storage.

## Functional Requirements

### 2.1 Book Management

#### Add Book
- The system should allow librarians to add new books to the library’s collection.
- Required fields: Title, Author, ISBN.
- Availability of an added book is set to true by default.

#### Remove Book
- The system should allow librarians to remove books from the collection.
- A book cannot be removed if there are pending transactions associated with it.

#### Update Book Availability
- The system should allow updating the availability status of books (True for available and false for unavailable).

#### View All Books
- The system should display a list of all books, including their details and availability status.

### 2.2 Patron Management

#### Add Patron
- The system should allow adding new patrons to the library.
- Required fields: Name, Email.

#### Remove Patron
- The system should allow removing patrons from the library.
- A patron cannot be removed if there are pending transactions associated with them.

#### View All Patrons
- The system should display a list of all patrons, including their details.

### 2.3 Transaction Management

#### Borrow Book
- Patrons should be able to borrow available books.
- Required fields: Book Name, Patron Name, Date Borrowed, Date Due.

#### Return Book
- Patrons should be able to return borrowed books.
- The system should update the transaction with the return date.

#### View Transaction History
- The system should display a list of all transactions, including book title, patron name, date borrowed, date due, and date returned.

## Non-Functional Requirements

### Usability
- The system should have a user-friendly interface that is easy to navigate for librarians.

### Reliability
- The system should be reliable and handle errors gracefully, ensuring data integrity.

## Technical Requirements

- **Programming Language**: Java
- **Database**: MySQL
- **Frameworks and Libraries**:
  - JavaFX for the user interface
  - JDBC for database connectivity

## System Components

### 5.1 User Class
- **Attributes**: name, email
- **Methods**: getters for attributes

### 5.2 Patron Class (extends User)
- **Attributes**: patronID (auto-incremented by the database)
- **Methods**: getters and setters for patronID, override toString for display purposes

### 5.3 Book Class
- **Attributes**: bookID (auto-incremented by the database), title, author, isbn, isAvailable
- **Methods**: getters and setters for all attributes

### 5.4 Transaction Class
- **Attributes**: transactionID (auto-incremented by the database), bookID, bookTitle, patronID, patronName, dateBorrowed, dateDue, dateReturned
- **Methods**: getters and setters for all attributes

### 5.5 BookDAO Class
- **Methods**:
  - `addBook(Book book)`: Adds a new book to the database.
  - `removeBook(int bookID)`: Removes a book from the database.
  - `updateBookAvailability(int bookID, boolean isAvailable)`: Updates the availability status of a book.
  - `getAllBooks()`: Retrieves all books from the database.

### 5.6 PatronDAO Class
- **Methods**:
  - `addPatron(Patron patron)`: Adds a new patron to the database.
  - `removePatron(int patronID)`: Removes a patron from the database.
  - `getAllPatrons()`: Retrieves all patrons from the database.

### 5.7 TransactionDAO Class
- **Methods**:
  - `addTransaction(Transaction transaction)`: Adds a new transaction to the database.
  - `updateTransaction(Transaction transaction)`: Updates an existing transaction (e.g., when a book is returned).
  - `getAllTransactions()`: Retrieves all transactions from the database.

### 5.8 JDBCUtil Class
- **Methods**:
  - `getConnection()`: Establishes and returns a connection to the MySQL database.

### 5.9 BookListManager Class
- **Attributes**: LinkedList<Book> books, BookDAO bookDAO
- **Methods**:
  - `addBook(Book book)`: Adds a book to the list and database.
  - `removeBook(int bookID)`: Removes a book from the list and database.
  - `updateBookAvailability(int bookID, boolean isAvailable)`: Updates the availability status of a book in the list and database.
  - `loadBooks()`: Loads all books from the database into the list.
  - `getBooks()`: Returns the list of books.

### 5.10 PatronListManager Class
- **Attributes**: List<Patron> patrons, PatronDAO patronDAO
- **Methods**:
  - `addPatron(Patron patron)`: Adds a patron to the list and database.
  - `removePatron(int patronID)`: Removes a patron from the list and database.
  - `loadPatrons()`: Loads all patrons from the database into the list.
  - `getPatrons()`: Returns the list of patrons.

### 5.11 MainApp Class
- **Methods**:
  - `start(Stage primaryStage)`: Initializes and starts the JavaFX application.
  - `initializeTables()`: Sets up the table views for books, patrons, and transactions.
  - `addBook(String title, String author, String isbn)`: Adds a new book.
  - `removeBook()`: Removes the selected book.
  - `showBorrowBookForm()`: Displays a form to borrow a book.
  - `borrowBook(Book book, Patron patron, java.sql.Date issueDate, java.sql.Date returnDate)`: Processes a book borrowing transaction.
  - `returnBook()`: Processes the return of a borrowed book.
  - `addPatron(String name, String email)`: Adds a new patron.
  - `removePatron()`: Removes the selected patron.
  - `checkIfBookHasTransactions(int bookID)`: Checks if a book has associated transactions.
  - `checkIfPatronHasTransactions(int patronID)`: Checks if a patron has associated transactions.
  - `showAlert(String message)`: Displays an alert message.

## Database Schema

### Books Table
- `BookID` (Primary Key, Auto-increment)
- `Title` (VARCHAR)
- `Author` (VARCHAR)
- `ISBN` (VARCHAR)
- `Available` (BOOLEAN)

### Patrons Table
- `PatronID` (Primary Key, Auto-increment)
- `Name` (VARCHAR)
- `Email` (VARCHAR)

### Transactions Table
- `TransactionID` (Primary Key, Auto-increment)
- `BookID` (Foreign Key)
- `PatronID` (Foreign Key)
- `DateBorrowed` (DATE)
- `DateDue` (DATE)
- `DateReturned` (DATE)
