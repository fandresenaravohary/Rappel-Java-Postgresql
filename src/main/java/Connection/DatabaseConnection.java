package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    private DatabaseConnection(){
        try {
            this.connection = DriverManager.getConnection(
                    System.getenv("DB_URL"),
                    System.getenv("DB_USER"),
                    System.getenv("DB_PASSWORD")
            );
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database: "+ e.getMessage());
        }
    }

    public static Connection getConnection() {
        if(connection == null){
            new DatabaseConnection();
        }
        return connection;
    }
}
