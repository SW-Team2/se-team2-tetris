package data.setting;

import java.awt.event.KeyEvent;

public class SettingData {
    private static SettingData mUniqueInstance = null;

    public Key mKey;
    public Screen mScreen;

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

    public class Screen {
        public int mWidth = 800;
        public int mHeight = 1040;
    }

    public int mColorBlindnessMod = 0;

    private SettingData() {
        mKey = new Key();
        mScreen = new Screen();
    }

    public static SettingData getInstance() {
        if (mUniqueInstance == null) {
            mUniqueInstance = new SettingData();
        }
        return mUniqueInstance;
    }
}
