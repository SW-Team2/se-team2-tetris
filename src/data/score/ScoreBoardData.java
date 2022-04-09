package data.score;

import data.DataBase;

import java.sql.*;
import java.util.ArrayList;

public class ScoreBoardData {
    private static ScoreBoardData uniqueInstance = null;

    private Connection connection;
    private ArrayList<Score> scoreDataList;

    private ScoreBoardData() {
        DataBase db = DataBase.getInstance();
        this.connection = db.getConnection();
        this.scoreDataList = new ArrayList<Score>(20);
        try {
            this.getScoreDataFromDB();
//            주석 삭제 예정 : 아래 statement를 주석해제하게 되면 db에 저장됩니다.
//            this.addNewScore(new Score("jy", 200, Difficulty.EASY.getValue(), Mode.ITEM_MODE.getValue()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ScoreBoardData getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ScoreBoardData();
        }
        return uniqueInstance;
    }

    public ArrayList<Score> getScoreDataList() {
        return scoreDataList;
    }

    private void getScoreDataFromDB() throws SQLException {
        ResultSet resultSet = null;
        Statement statement = null;

        statement = this.connection.createStatement();
        resultSet = statement.executeQuery("select * from score order by score desc");
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
    }

    public void addNewScore(Score newValue) throws SQLException {
        PreparedStatement statement = null;

        String query = "insert into score(name, score, difficulty, gameMode) values (?, ?, ?, ?)";
        statement = this.connection.prepareStatement(query);
        statement.setString(1, newValue.getName());
        statement.setInt(2, newValue.getScore());
        statement.setString(3, newValue.getDifficulty());
        statement.setString(4, newValue.getGameMode());

        statement.executeUpdate();

        statement.close();
    }

}
