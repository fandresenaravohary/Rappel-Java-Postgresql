package library_management.Repository;

import library_management.Connection.model.Topic;
import library_management.model.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorCrudOperations implements CrudOperations<Author> {
    private final Connection connection;
    public AuthorCrudOperations(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();

        String sql = "SELECT * FROM author";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getLong("id"));
                author.setAuthorName(resultSet.getString("name"));
                author.setSex(resultSet.getString("sex").charAt(0));

                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authors;
    }

    @Override
    public List<Author> saveAll(List<Author> toSave) {
        String sql = "INSERT INTO author (name, sex) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (Author author : toSave) {
                statement.setString(1, author.getAuthorName());
                statement.setString(2, String.valueOf(author.getSex()));

                statement.addBatch();
            }
            statement.executeBatch();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                int i = 0;
                while (generatedKeys.next()) {
                    toSave.get(i).setId(generatedKeys.getLong(1));
                    i++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }


    @Override
    public Author save(Author toSave) {
        String sql = "UPDATE author SET authorName = ?, sex = ? WHERE id = ?";
        try( PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, toSave.getAuthorName());
            statement.setString(2, String.valueOf(toSave.getSex()));

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toSave;
    }

    @Override
    public Author delete(Author toDelete) {
        String sql = "DELETE FROM author WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, toDelete.getId());
            statement.executeUpdate();
            return toDelete;
        } catch (SQLException ex) {
            throw new RuntimeException("Error while suppressing");
        }
    }
}
