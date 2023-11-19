import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            // Créer une instance de BookCrudOperations avec la connexion à la base de données
            BookCrudOperations bookCrudOperations = new BookCrudOperations(connection);

            // Tester la méthode findAll
            List<Book> allBooks = bookCrudOperations.findAll();
            logger.log(Level.INFO, "All Books: {0}", allBooks);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while connecting to the database", e);
        }
    }
}
