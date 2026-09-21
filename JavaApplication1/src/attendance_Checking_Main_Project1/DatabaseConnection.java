package attendance_Checking_Main_Project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Centralizes the application's database configuration and connection creation. */
public final class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/attendance_checking";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private DatabaseConnection() {
        // Utility class.
    }

    /** Opens a new JDBC connection. Callers must close it with try-with-resources. */
    public static Connection open() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
