package data.score;

import java.sql.*;

public class ScoreBoardInfo {
    private static ScoreBoardInfo mUniqueInstance = null;

    private ScoreInfo mScoreInfos[];
    private static final int mPersonnel = 10;

    private ScoreBoardInfo() {
        try {
            this.getDataFromDB();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ScoreBoardInfo getInstance() {
        if (mUniqueInstance == null) {
            mUniqueInstance = new ScoreBoardInfo();
        }
        return mUniqueInstance;
    }

    public void getDataFromDB() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/tetrisgame";
        String user = "root";
        String password = "dhwjdwls";

        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = null;

        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
        resultSet = statement.executeQuery("select * from users");

        resultSet.next();
        String name = resultSet.getString("name");
        System.out.println(name);
        resultSet.close();
        statement.close();
        connection.close();

    }

    public boolean addNewScore(String name, long score) {
        boolean addable = true;
        // TODO:
        return addable;
    }
}
