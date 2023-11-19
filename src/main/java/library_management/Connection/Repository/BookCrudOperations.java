package Repository;

import model.Book;
import model.Topic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;


public class BookCrudOperations implements CrudOperations<Book>{
    private final Connection connection;
    public BookCrudOperations(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM book";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setBookName(resultSet.getString("book_name"));
                book.setPageNumbers(resultSet.getInt("page_numbers"));
                book.setSex(resultSet.getString("sex").charAt(0));
                book.setTopic(Topic.valueOf(resultSet.getString("topic")));
                book.setReleaseDate(resultSet.getObject("release_date", LocalDate.class));
                book.setAuthorId(resultSet.getLong("author_id"));

                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public List<Book> saveAll(List<Book> toSave) {
        String sql = "INSERT INTO book (book_name, page_numbers, sex, topic, release_date, author_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < toSave.size(); i++) {
                Book book = toSave.get(i);

                statement.setString(1, book.getBookName());
                statement.setInt(2, book.getPageNumbers());
                statement.setString(3, String.valueOf(book.getSex()));
                statement.setString(4, book.getTopic().toString());
                statement.setObject(5, book.getReleaseDate());
                statement.setLong(6, book.getAuthorId());

                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toSave;
    }


    @Override
    public Book save(Book toSave) {
        String sql = "UPDATE book SET book_name = ?, page_numbers = ?, sex = ?, topic = ?, release_date = ?, author_id = ? WHERE id = ?";
        try( PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, toSave.getBookName());
            statement.setInt(2, toSave.getPageNumbers());
            statement.setString(3, String.valueOf(toSave.getSex()));
            statement.setString(4, toSave.getTopic().toString());
            statement.setObject(5, toSave.getReleaseDate());
            statement.setLong(6, toSave.getAuthorId());
            statement.setLong(7, toSave.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
         return toSave;
    }

    @Override
    public Book delete(Book toDelete) {
        String sql = "DELETE FROM Appointment WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, toDelete.getId());
            statement.executeUpdate();
            return toDelete;
        } catch (SQLException ex) {
            throw new RuntimeException("Error while suppressing");
        }
    }
}
