package data.score;

import java.sql.*;
import java.util.ArrayList;

public class ScoreBoardData {
    private static ScoreBoardData uniqueInstance = null;

    private Connection connection;
    private ArrayList<Score> scoreDataList;

    private ScoreBoardData() {
        connectDB();
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
                String URL = "jdbc:sqlite:/Users/jeongjin/IdeaProjects/se-team2-tetris/database/tetrisgame";
                this.connection = DriverManager.getConnection(URL);

                System.out.println("Connection to SQLite has been established.");

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }

    public Connection getConnection() {
        return this.connection;
    }

    public ArrayList<Score> getScoreDataList() {
        return scoreDataList;
    }

    private ArrayList<Score> getDefaultModeScore() throws SQLException {
        connectDB();
        ResultSet resultSet = null;
        Statement statement = null;

        scoreDataList = new ArrayList<Score>(20);

        statement = this.connection.createStatement();
        resultSet = statement.executeQuery("select * from scores where difficulty is not null order by score desc limit 10");
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int score = resultSet.getInt("score");
            String difficulty = resultSet.getString("difficulty");
            String gameMode = resultSet.getString("gameMode");
            Score scoreData = new Score(name, score, difficulty, gameMode);
            scoreDataList.add(scoreData);
        }
        resultSet.close();
        statement.close();
        closeConnection();

        return scoreDataList;
    }

    private ArrayList<Score> getItemModeScore() throws SQLException {
        connectDB();
        ResultSet resultSet = null;
        Statement statement = null;
        scoreDataList = new ArrayList<Score>(20);

        statement = this.connection.createStatement();
        resultSet = statement.executeQuery("select * from scores where gameMode is not null order by score desc limit 10");
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int score = resultSet.getInt("score");
            String difficulty = resultSet.getString("difficulty");
            String gameMode = resultSet.getString("gameMode");
            Score scoreData = new Score(name, score, difficulty, gameMode);
            scoreDataList.add(scoreData);
        }
        resultSet.close();
        statement.close();
        closeConnection();

        return scoreDataList;
    }

    public void addDefaultModeScore(Score newValue) throws SQLException {
        connectDB();

        PreparedStatement statement;

        String query = "insert into score(name, score, difficulty) values (?, ?, ?)";
        statement = this.connection.prepareStatement(query);
        statement.setString(1, newValue.getName());
        statement.setInt(2, newValue.getScore());
        statement.setString(3, newValue.getDifficulty());

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

}
