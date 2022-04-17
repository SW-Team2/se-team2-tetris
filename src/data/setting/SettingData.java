package data.setting;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SettingData {
    private static SettingData uniqueInstance = null;

    private Connection connection;

    public Key mKey;
    public Screen mScreen;

    public class Screen {
        public int mWidth = 800;
        public int mHeight = 1040;
    }
    class Key {
        public Menu mMenu;
        public Game mGame;

        public class Menu {
            public int mMoveUp = KeyEvent.VK_UP;
            public int mMoveDown = KeyEvent.VK_DOWN;
            public int mMoveRight = KeyEvent.VK_RIGHT;
            public int mMoveLeft = KeyEvent.VK_LEFT;
            public int mSelect = KeyEvent.VK_ENTER;
        }

        public class Game {
            public int mMoveDown = KeyEvent.VK_DOWN;
            public int mMoveRight = KeyEvent.VK_RIGHT;
            public int mMoveLeft = KeyEvent.VK_LEFT;
            public int mMoveToFloor = KeyEvent.VK_UP;
            public int mRotate = KeyEvent.VK_SPACE;
            public int mPause = KeyEvent.VK_P;
        }
    }

    private SettingData() {
        connectDB();
    }

    public static SettingData getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SettingData();
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

    public void getSettingData() {

    }

    public void setInitialData() {
//        TODO: initialize Setting Data
    }
    public void setWindowSize() {

    }
    public void setGameKey() {

    }
    public void setMenuKey() {

    }
    public void setColorBlindnessMode() {

    }
}
