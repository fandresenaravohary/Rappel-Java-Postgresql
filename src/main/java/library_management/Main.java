package library_management;

import library_management.Connection.DatabaseConnection;
import library_management.Repository.AuthorCrudOperations;
import library_management.Repository.BookCrudOperations;
import library_management.Repository.SubscriberCrudOperations;
import library_management.model.Author;
import library_management.model.Book;
import library_management.model.Subscriber;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class Main {
    private static final Logger logger = Logger.getLogger(String.valueOf(Main.class));
    private static AuthorCrudOperations authorCrud;

    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();

        AuthorCrudOperations authorCrud = new AuthorCrudOperations(connection);
        testAuthorCrudOperations(authorCrud);

        BookCrudOperations bookCrud = new BookCrudOperations(connection);
        testBookCrudOperations(bookCrud);

        SubscriberCrudOperations subscriberCrud = new SubscriberCrudOperations(connection);
        testSubscriberCrudOperations(subscriberCrud);

        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error while closing the database connection: " + e.getMessage());
        }
    }

    private static void testSubscriberCrudOperations(SubscriberCrudOperations subscriberCrud) {
        List<Subscriber> allSubscribers = subscriberCrud.findAll();
        System.out.println("All Subscribers: " + allSubscribers);

        List<Subscriber> subscribersToSave = new ArrayList<>();
        subscribersToSave.add(new Subscriber("Alice", "ref-001"));
        subscribersToSave.add(new Subscriber("Bob", "ref-002"));

        List<Subscriber> savedSubscribers = subscriberCrud.saveAll(subscribersToSave);
        System.out.println("Saved Subscribers: " + savedSubscribers);

        List<Subscriber> updatedSubscribers = subscriberCrud.findAll();
        System.out.println("Updated Subscribers: " + updatedSubscribers);

        Subscriber subscriberToDelete = updatedSubscribers.get(0);
        Subscriber deletedSubscriber = subscriberCrud.delete(subscriberToDelete);
        System.out.println("Deleted Subscriber: " + deletedSubscriber);

        List<Subscriber> remainingSubscribers = subscriberCrud.findAll();
        System.out.println("Remaining Subscribers: " + remainingSubscribers);
    }

    private static void testAuthorCrudOperations(AuthorCrudOperations authorCrud) {
        System.out.println("Testing AuthorCrudOperations...");

        List<Author> allAuthors = authorCrud.findAll();
        System.out.println("All Authors: " + allAuthors);

        List<Author> authorsToSave = new ArrayList<>();
        authorsToSave.add(new Author("John Doe", 'M'));
        authorsToSave.add(new Author("Jane Smith", 'F'));

        List<Author> savedAuthors = authorCrud.saveAll(authorsToSave);
        System.out.println("Saved Authors: " + savedAuthors);

        Author authorToUpdate = savedAuthors.get(0);
        authorToUpdate.setAuthorName("Updated Author");
        Author updatedAuthor = authorCrud.save(authorToUpdate);
        System.out.println("Updated Author: " + updatedAuthor);

        Author authorToDelete = savedAuthors.get(1);
        Author deletedAuthor = authorCrud.delete(authorToDelete);
        System.out.println("Deleted Author: " + deletedAuthor);

        List<Author> updatedAllAuthors = authorCrud.findAll();
        System.out.println("All Authors After Operations: " + updatedAllAuthors);
    }

    private static void testBookCrudOperations(BookCrudOperations bookCrud) {
        List<Book> allBooks = bookCrud.findAll();
        System.out.println("All Books: " + allBooks);

        List<Book> booksToSave = new ArrayList<>();
        booksToSave.add(new Book("Book 1", 200, 'M', library_management.Connection.model.Topic.COMEDY, LocalDate.now(), 1L));
        booksToSave.add(new Book("Book 2", 150, 'F', library_management.Connection.model.Topic.ROMANCE, LocalDate.now(), 2L));

        List<Book> savedBooks = bookCrud.saveAll(booksToSave);
        System.out.println("Saved Books: " + savedBooks);

        List<Book> updatedBooks = bookCrud.findAll();
        System.out.println("Updated Books: " + updatedBooks);

        Book bookToDelete = updatedBooks.get(0);
        Book deletedBook = bookCrud.delete(bookToDelete);
        System.out.println("Deleted Book: " + deletedBook);

        List<Book> remainingBooks = bookCrud.findAll();
        System.out.println("Remaining Books: " + remainingBooks);
    }



}
