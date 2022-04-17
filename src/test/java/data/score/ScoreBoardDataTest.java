package data.score;

import org.assertj.db.type.*;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.db.api.Assertions.assertThat;
import static org.assertj.db.output.Outputs.output;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.assertj.db.type.Table;

import java.sql.Connection;
import java.sql.SQLException;


class ScoreBoardDataTest {
    protected static JdbcConnectionPool dataSource;
    private static final String SCORES_CREATE_REQUEST = "create table scores(id number primary key ,name varchar not null,score integer not null, difficulty varchar, gameMode varchar);";
    private static final String[] SCORES_INSERT_REQUESTS = {
            "insert into scores values (1, 'Hewson', 123, null,'Default');",
            "insert into scores values (2, 'jj', 1231, 'Hard',null);",
            "insert into scores values (3, 'sy', 321, 'Easy',null);",
            "insert into scores values (4, 'tw', 100, null,'Default');",
            "insert into scores values (5, 'Hewson', 123, null,'Default');",
            "insert into scores values (6, 'jj', 1231, 'Hard',null);",
            "insert into scores values (7, 'sy', 321, 'Easy',null);",
            "insert into scores values (8, 'tw', 100, null,'Default');",
            "insert into scores values (9, 'Hewson', 123, null,'Default');",
            "insert into scores values (10, 'jj', 1231, 'Hard',null);",
            "insert into scores values (11, 'sy', 321, 'Easy',null);",
            "insert into scores values (12, 'tw', 100, null,'Default');",
            "insert into scores values (13, 'Hewson', 123, null,'Default');",
            "insert into scores values (14, 'jj', 1231, 'Hard',null);",
            "insert into scores values (15, 'sy', 321, 'Easy',null);",
            "insert into scores values (16, 'tw', 100, null,'Default');",
            "insert into scores values (17, 'Hewson', 123, null,'Default');",
            "insert into scores values (18, 'jj', 1231, 'Hard',null);",
            "insert into scores values (19, 'sy', 321, 'Easy',null);",
            "insert into scores values (20, 'tw', 100, null,'Default');",
            "insert into scores values (21, 'Hewson', 123, null,'Default');",
            "insert into scores values (22, 'jj', 1231, 'Hard',null);",
            "insert into scores values (23, 'sy', 321, 'Easy',null);",
            "insert into scores values (24, 'tw', 100, null,'Default');",
    };

    protected void makeDefaultScore() throws SQLException {
        Connection conn = dataSource.getConnection();
        conn.createStatement().executeUpdate("insert into scores(id, name, score, difficulty) values (500, 'jy', 200, 'Hard');");
        conn.createStatement().executeUpdate("insert into scores(id, name, score, difficulty) values (600, 'jd', 10, 'Normal');");
        conn.close();
    }

    protected void makeItemScore() throws SQLException {
        Connection conn = dataSource.getConnection();
        conn.createStatement().executeUpdate("insert into scores(id, name, score, gameMode) values (510, 'jy', 200, 'Default');");
        conn.createStatement().executeUpdate("insert into scores(id, name, score, gameMode) values (610, 'jd', 10, 'item');");
        conn.close();
    }

    @Test
    public void connectDB() {
        ScoreBoardData scoreboard = ScoreBoardData.getInstance();
        assertNotNull(scoreboard.getConnection());
    }

    @Test
    void getScoreDataList() {

    }

    @BeforeEach
    public void setUp() throws SQLException {
        if (dataSource == null) {
            dataSource = JdbcConnectionPool.create("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        } else {
            Connection con = dataSource.getConnection();
            con.createStatement().executeUpdate("drop table scores;");
            con.close();

        }
        Connection con = dataSource.getConnection();
        con.createStatement().executeUpdate(SCORES_CREATE_REQUEST);
        for (String request : SCORES_INSERT_REQUESTS) {
            con.createStatement().executeUpdate(request);
        }
        con.close();
    }

    @Test
    public void getDefaultModeScore() throws SQLException {
        Request request = new Request(dataSource, "select * from scores where difficulty is not null order by score desc limit 10");

        assertThat(request).hasNumberOfRows(10);

        output(request).toConsole();
    }

    @Test
    public void getItemModeScore() throws SQLException {
        Request request = new Request(dataSource, "select * from scores where gameMode is not null order by score desc limit 10");

        assertThat(request).hasNumberOfRows(10);

        output(request).toConsole();
    }


    @Test
    public void addDefaultModeScore() throws SQLException {
        Changes changes = new Changes(dataSource);

        changes.setStartPointNow();
        makeDefaultScore();
        changes.setEndPointNow();

        assertThat(changes).ofCreation().hasNumberOfChanges(2);
    }

    @Test
    public void addItemModeScore() throws SQLException {
        Changes changes = new Changes(dataSource);

        changes.setStartPointNow();
        makeItemScore();
        changes.setEndPointNow();

        assertThat(changes).ofCreation().hasNumberOfChanges(2);
    }
}