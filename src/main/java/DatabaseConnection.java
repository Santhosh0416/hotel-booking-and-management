import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final String PROPERTIES_FILE = "config.properties";

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        try {
            // Load properties from a properties file
            properties.load(DatabaseConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String dbUrl = properties.getProperty("DB_URL");
        String dbUser = properties.getProperty("DB_USER");
        String dbPassword = properties.getProperty("DB_PASSWORD");

        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}