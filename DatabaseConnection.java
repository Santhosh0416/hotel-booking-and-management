package com.hotel.db;

import com.hotel.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class - FIXED: Uses ConfigUtil for credentials
 */
public class DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
    
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;

    static {
        try {
            // Load configuration from config.properties
            ConfigUtil config = new ConfigUtil();
            DB_URL = config.getDatabaseUrl();
            DB_USER = config.getDatabaseUsername();
            DB_PASSWORD = config.getDatabasePassword();
            
            // Load JDBC driver
            Class.forName(DRIVER_CLASS);
            logger.info("MySQL Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            logger.error("MySQL Driver not found: ", e);
        } catch (Exception e) {
            logger.error("Error loading configuration: ", e);
        }
    }

    /**
     * Get a database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            logger.info("Database connection established");
            return conn;
        } catch (SQLException e) {
            logger.error("Failed to connect to database: ", e);
            throw e;
        }
    }

    /**
     * Close a database connection
     * @param conn Connection to close
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                logger.info("Database connection closed");
            } catch (SQLException e) {
                logger.error("Error closing connection: ", e);
            }
        }
    }
}
