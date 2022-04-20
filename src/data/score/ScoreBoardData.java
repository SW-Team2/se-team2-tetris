package data.score;

import java.sql.*;
import java.util.ArrayList;

public class ScoreBoardData {
    private static ScoreBoardData uniqueInstance = null;

    private Connection connection;
    private ArrayList<Score> defaultModeScores;
    private ArrayList<Score> itemModeScores;

    private final int MAX_ROWS = 10;
    private final String URL = "jdbc:sqlite:" + System.getProperty("user.dir") + "/database/tetrisgame";


    private ScoreBoardData() {
        createScoresTable();
    }

    public static ScoreBoardData getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ScoreBoardData();
        }
        return uniqueInstance;
    }

    private void connectDB() {
        if (this.connection == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                this.connection = DriverManager.getConnection(URL);
                System.out.println("Connection to SQLite has been established.");

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }

    public void createScoresTable() {
        connectDB();
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String query = "create table if not exists scores(id integer not null constraint scores_pk primary key autoincrement," +
                    " name varchar(20) not null," +
                    " score integer not null," +
                    " difficulty varchar(20)," +
                    " gameMode varchar(20)" +
                    ")";
            statement.executeUpdate(query);

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public ArrayList<Score> getDefaultModeScore() throws SQLException {
        connectDB();
        ResultSet resultSet = null;
        Statement statement = null;

        this.defaultModeScores = new ArrayList<>();

        statement = this.connection.createStatement();
        statement.setMaxRows(MAX_ROWS);
        resultSet = statement.executeQuery("select * from scores where difficulty is not null order by score desc");
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int score = resultSet.getInt("score");
            String difficulty = resultSet.getString("difficulty");
            String gameMode = resultSet.getString("gameMode");
            Score scoreData = new Score(name, score, difficulty, gameMode);
            this.defaultModeScores.add(scoreData);
        }
        resultSet.close();
        statement.close();
        closeConnection();

        return this.defaultModeScores;
    }

    public ArrayList<Score> getItemModeScore() throws SQLException {
        connectDB();
        ResultSet resultSet = null;
        Statement statement = null;
        this.itemModeScores = new ArrayList<>();

        statement = this.connection.createStatement();
        statement.setMaxRows(MAX_ROWS);
        resultSet = statement.executeQuery("select * from scores where gameMode is not null order by score desc");
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int score = resultSet.getInt("score");
            String difficulty = resultSet.getString("difficulty");
            String gameMode = resultSet.getString("gameMode");
            Score scoreData = new Score(name, score, difficulty, gameMode);
            this.itemModeScores.add(scoreData);
        }
        resultSet.close();
        statement.close();
        closeConnection();

        return this.itemModeScores;
    }

    public void addDefaultModeScore(Score newValue) throws SQLException {
        connectDB();

        PreparedStatement statement;

        String query = "insert into score(name, score, difficulty, gameMode) values (?, ?, ?, ?)";
        statement = this.connection.prepareStatement(query);
        statement.setString(1, newValue.getName());
        statement.setInt(2, newValue.getScore());
        statement.setString(3, newValue.getDifficulty());
        statement.setString(4, newValue.getGameMode());

        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    public void addItemModeScore(Score newValue) throws SQLException {
        connectDB();

        PreparedStatement statement;

        String query = "insert into score(name, score, gameMode) values (?, ?, ?)";
        statement = this.connection.prepareStatement(query);
        statement.setString(1, newValue.getName());
        statement.setInt(2, newValue.getScore());
        statement.setString(3, newValue.getGameMode());

        statement.executeUpdate();
        statement.close();
        closeConnection();
    }

    public void resetScoreBoard() throws SQLException {
        connectDB();
        Statement statement = null;

        statement = this.connection.createStatement();
        String query = "delete from scores";
        statement.executeUpdate(query);

        statement.close();
        closeConnection();
    }
}
