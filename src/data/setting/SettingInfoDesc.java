package data.setting;

import java.awt.event.KeyEvent;

public class SettingInfoDesc {
    static SettingInfoDesc mUniqueInstance = null;

    public class Key {
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

    public int mColorBlindnessMod = 0;

    private SettingInfoDesc() {
        // TODO:
    }

    public static SettingInfoDesc getInstance() {
        if (mUniqueInstance == null) {
            mUniqueInstance = new SettingInfoDesc();
        }
        return mUniqueInstance;
    }
}
