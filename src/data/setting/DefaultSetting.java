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

        playerFirstKey.put("moveDown", KeyEvent.VK_S);
        playerFirstKey.put("moveRight", KeyEvent.VK_A);
        playerFirstKey.put("moveLeft", KeyEvent.VK_D);
        playerFirstKey.put("moveToFloor", KeyEvent.VK_W);
        playerFirstKey.put("rotate", KeyEvent.VK_T);

        playerSecondKey.put("moveDown", KeyEvent.VK_DOWN);
        playerSecondKey.put("moveRight", KeyEvent.VK_RIGHT);
        playerSecondKey.put("moveLeft", KeyEvent.VK_LEFT);
        playerSecondKey.put("moveToFloor", KeyEvent.VK_UP);
        playerSecondKey.put("rotate", KeyEvent.VK_SLASH);
    }

    public static DefaultSetting getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new DefaultSetting();
        }
        return uniqueInstance;
    }

    public HashMap<String, Integer> getWindowSize() {
        return (HashMap<String, Integer>) windowSize.clone();
    }

    public HashMap<String, Integer> getGameKeyMap() {
        return (HashMap<String, Integer>) gameKeyMap.clone();
    }

    public int getBlindnessMode() {
        return blindnessMode;
    }

    public HashMap<String, Integer> getPlayerFirstKeyMap() {
        return (HashMap<String, Integer>) playerFirstKey.clone();
    }

    public HashMap<String, Integer> getPlayerSecondKeyMap() {
        return (HashMap<String, Integer>) playerSecondKey.clone();
    }
}
