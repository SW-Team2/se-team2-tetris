package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    private static DataBase uniqueInstance = null;
    private Connection connection;

    private DataBase() {
        try {
            connectDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connectDB() throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/tetrisgame";
        String USER = "root";
        String PASSWORD = "dhwjdwls";
        this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public Connection getConnection() {
        return this.connection;
    }


    public void closeConnection() throws SQLException {
        this.connection.close();
    }

    public static DataBase getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new DataBase();
        }
        return uniqueInstance;
    }

}
