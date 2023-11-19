package library_management.Repository;

import library_management.model.Book;
import library_management.model.Subscriber;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static library_management.Connection.DatabaseConnection.connection;

public class SubscriberCrudOperations implements CrudOperations<Subscriber>{
    private final Connection connection;

    public SubscriberCrudOperations(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Subscriber> findAll() {
        List<Subscriber> subscribers = new ArrayList<>();
        String sql = "SELECT * FROM subscribers";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Subscriber subscriber = new Subscriber();
                subscriber.setId(resultSet.getLong("id"));
                subscriber.setName(resultSet.getString("name"));
                subscriber.setReference(resultSet.getString("reference"));

                subscribers.add(subscriber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscribers;
    }


    @Override
    public List<Subscriber> saveAll(List<Subscriber> toSave) {
        String sql = "INSERT INTO subscribers (name, reference) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Subscriber subscriber : toSave) {
                statement.setString(1, subscriber.getName());
                statement.setString(2, subscriber.getReference());

                statement.addBatch();
            }
            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toSave;
    }


    @Override
    public Subscriber save(Subscriber toSave) {
        String sql = "UPDATE subscribers SET name = ?, reference = ? WHERE id = ?";
        try( PreparedStatement statement = connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, toSave.getName());
            statement.setString(2, toSave.getReference());

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    toSave.setId(generatedKeys.getLong(1));
                }
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toSave;
    }

    @Override
    public Subscriber delete(Subscriber toDelete) {
        String sql = "DELETE FROM subscribers WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, toDelete.getId());
            statement.executeUpdate();
            return toDelete;
        } catch (SQLException ex) {
            throw new RuntimeException("Error while suppressing");
        }
    }
}
