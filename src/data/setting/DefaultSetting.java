package data.setting;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class DefaultSetting {
    private static DefaultSetting uniqueInstance = null;

    private HashMap<String, Integer> windowSize;
    private HashMap<String, Integer> gameKeyMap;
    private final int blindnessMode;

    private DefaultSetting() {
        windowSize = new HashMap<>();
        gameKeyMap = new HashMap<>();

        windowSize.put("width", 800);
        windowSize.put("height", 1040);

        gameKeyMap.put("moveDown", KeyEvent.VK_DOWN);
        gameKeyMap.put("moveRight", KeyEvent.VK_RIGHT);
        gameKeyMap.put("moveLeft", KeyEvent.VK_LEFT);
        gameKeyMap.put("moveToFloor", KeyEvent.VK_UP);
        gameKeyMap.put("rotate", KeyEvent.VK_SPACE);
        gameKeyMap.put("pause", KeyEvent.VK_P);

        this.blindnessMode = BlindMode.NONE.ordinal();
    }

    public static DefaultSetting getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new DefaultSetting();
        }
        return uniqueInstance;
    }

    public HashMap<String, Integer> getWindowSize() {
        return windowSize;
    }

    public HashMap<String, Integer> getGameKeyMap() {
        return gameKeyMap;
    }

    public int getBlindnessMode() {
        return blindnessMode;
    }
}
