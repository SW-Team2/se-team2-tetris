package data.score;

import org.assertj.db.type.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestInstance;

import java.sql.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScoreBoardDataTest {
    private static final String[] SCORES_INSERT_REQUESTS = {
            "insert into scores(name, score, difficulty, gameMode) values ('defalut', 1, 'Hard','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('lastDefault', 2, 'Hard','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('haha', 3, 'Easy','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 4, 'Hard','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 5, 'Hard','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('sy', 6, 'Easy','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 7, 'Hard','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 8, 'Hard','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('kk', 9, 'Easy','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('asdf', 10, 'Hard','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('ll', 11, 'Hard','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('item', 1, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('lastItem', 2, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('haha', 3, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('sy', 4, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 5, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 6, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 7, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 8, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('sy', 9, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('asdf', 10, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('qwer', 11, null,'Item')",

    };
    private final String URL = "jdbc:sqlite:" + System.getProperty("user.dir") + "/database/tetrisgame";
    private Connection connection;

    protected void makeDefaultScore() throws SQLException {
        connection.createStatement().executeUpdate("insert into scores(name, score, difficulty) values ('jy', 200, 'Hard')");
        connection.createStatement().executeUpdate("insert into scores(name, score, difficulty) values ('jd', 10, 'Normal')");
    }

    protected void makeItemScore() throws SQLException {
        connection.createStatement().executeUpdate("insert into scores(name, score, gameMode) values ( 'jy', 200, 'Default')");
        connection.createStatement().executeUpdate("insert into scores(name, score, gameMode) values ('jd', 10, 'item')");
    }

    @BeforeAll
    public void setUp() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (String request : SCORES_INSERT_REQUESTS) {
            connection.createStatement().executeUpdate(request);
        }
    }

    @Test
    public void getDefaultModeScore() throws SQLException {
        Statement statement = connection.createStatement();
        statement.setMaxRows(10);
        ResultSet resultSet = statement.executeQuery("select * from scores where gameMode = 'Default' order by score desc");

        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.getString("name")).isEqualTo("lastDefault");

        statement.close();
    }

    @Test
    public void getItemModeScore() throws SQLException {
        Statement statement = connection.createStatement();
        statement.setMaxRows(10);
        ResultSet resultSet = statement.executeQuery("select * from scores where gameMode = 'Item' order by score desc");

        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.getString("name")).isEqualTo("lastItem");

        statement.close();
    }

    @AfterAll
    public void reset() throws SQLException {
        Statement statement = null;

        statement = connection.createStatement();
        statement.executeUpdate("delete from scores");

        statement.close();
        connection.close();
    }
}