package data.setting;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class DefaultSetting {
    private static DefaultSetting uniqueInstance = null;

    private HashMap<String, Integer> windowSize;
    private HashMap<String, Integer> gameKeyMap;
    private HashMap<String, Integer> playerFirstKey;
    private HashMap<String, Integer> playerSecondKey;
    private final int blindnessMode;

    private DefaultSetting() {
        windowSize = new HashMap<>();
        gameKeyMap = new HashMap<>();
        playerFirstKey = new HashMap<>();
        playerSecondKey = new HashMap<>();

        windowSize.put("width", 720);
        windowSize.put("height", 720);

        gameKeyMap.put("moveDown", KeyEvent.VK_DOWN);
        gameKeyMap.put("moveRight", KeyEvent.VK_RIGHT);
        gameKeyMap.put("moveLeft", KeyEvent.VK_LEFT);
        gameKeyMap.put("moveToFloor", KeyEvent.VK_UP);
        gameKeyMap.put("rotate", KeyEvent.VK_SPACE);
        gameKeyMap.put("pause", KeyEvent.VK_P);

        this.blindnessMode = BlindMode.NONE.ordinal();

        playerFirstKey.put("moveDown", KeyEvent.VK_DOWN);
        playerFirstKey.put("moveRight", KeyEvent.VK_RIGHT);
        playerFirstKey.put("moveLeft", KeyEvent.VK_LEFT);
        playerFirstKey.put("moveToFloor", KeyEvent.VK_UP);
        playerFirstKey.put("rotate", KeyEvent.VK_SLASH);

        playerSecondKey.put("moveDown", KeyEvent.VK_S);
        playerSecondKey.put("moveRight", KeyEvent.VK_A);
        playerSecondKey.put("moveLeft", KeyEvent.VK_D);
        playerSecondKey.put("moveToFloor", KeyEvent.VK_W);
        playerSecondKey.put("rotate", KeyEvent.VK_T);
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

    public HashMap<String, Integer> getPlayerFirstKeyMap() {
        return playerFirstKey;
    }

    public HashMap<String, Integer> getPlayerSecondKeyMap() {
        return playerSecondKey;
    }
}
