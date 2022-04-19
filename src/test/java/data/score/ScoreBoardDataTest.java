package data.score;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestInstance;

import java.sql.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScoreBoardDataTest {
    private static final String[] SCORES_INSERT_REQUESTS = {
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 1231, 'Hard','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('asdf', 1231, 'Hard','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('sy', 321, 'Easy','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 1231, 'Hard','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 1231, 'Hard','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('sy', 321, 'Easy','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 1231, 'Hard','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 1231, 'Hard','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('sy', 321, 'Easy','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 1231, 'Hard','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 1231, 'Hard','Default')",
            "insert into scores(name, score, difficulty, gameMode) values ('sy', 321, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 1231, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 7124, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('sy', 3213, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 231, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 61, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 1231, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('jj', 1231, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('sy', 483, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('itemTarget', 23, null,'Item')",
            "insert into scores(name, score, difficulty, gameMode) values ('qwer', 561, null,'Item')",

    };
    private final String URL = "jdbc:sqlite:"+ System.getProperty("user.dir") +"/database/tetrisgame";
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
        assertThat(resultSet.getString("name")).isEqualTo("itemTarget");

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
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.getString("name")).isEqualTo("itemTarget");
        assertThat(resultSet.next()).isFalse();

        statement.close();
    }


    @Test
    public void addDefaultModeScore() throws SQLException {
    }

    @Test
    public void addItemModeScore() throws SQLException {
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