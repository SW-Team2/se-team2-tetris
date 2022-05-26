package data.setting;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("unchecked")
public class SettingData {
    private static SettingData uniqueInstance = null;

    private HashMap<String, Integer> windowSize;
    private HashMap<String, Integer> gameKey;
    private int blindnessMode;
    private HashMap<String, Integer> playerFirstKey;
    private HashMap<String, Integer> playerSecondKey;

    private final String PATH = System.getProperty("user.dir") + "/database/Setting.json";

    private SettingData() {
        initialize();
    }

    public static SettingData getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SettingData();
        }
        return uniqueInstance;
    }

    public void initialize() {
        File file = new File(PATH);

        try {
            if (file.createNewFile()) {
                resetSetting();
            } else {
                JSONParser jsonParser = new JSONParser();

                FileReader reader = new FileReader(PATH);
                JSONObject obj = (JSONObject) jsonParser.parse(reader);

                this.windowSize = (HashMap<String, Integer>) obj.get("windowSize");
                this.gameKey = (HashMap<String, Integer>) obj.get("gameKey");
                this.blindnessMode = Integer.parseInt(String.valueOf(obj.get("blindnessMode")));
                this.playerFirstKey = (HashMap<String, Integer>) obj.get("player1Key");
                this.playerSecondKey = (HashMap<String, Integer>) obj.get("player2Key");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            DefaultSetting defaultSetting = DefaultSetting.getInstance();

            this.windowSize = defaultSetting.getWindowSize();
            this.gameKey = defaultSetting.getGameKeyMap();
            this.blindnessMode = defaultSetting.getBlindnessMode();
            this.playerFirstKey = defaultSetting.getPlayerFirstKeyMap();
            this.playerSecondKey = defaultSetting.getPlayerSecondKeyMap();
        }
    }

    public void storeWindowSize(int width, int height) {
        this.windowSize.put("width", width);
        this.windowSize.put("height", height);

        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(PATH);
            JSONObject setting = (JSONObject) jsonParser.parse(reader);

            setting.put("windowSize", this.windowSize);

            FileWriter writer = new FileWriter(PATH);
            writer.write(setting.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void storeGameMoveDownKey(int moveDown) {
        this.gameKey.put("moveDown", moveDown);

        storeGameKey();
    }

    public void storeGameMoveRightKey(int moveRight) {
        this.gameKey.put("moveRight", moveRight);

        storeGameKey();
    }

    public void storeGameMoveLeftKey(int moveLeft) {
        this.gameKey.put("moveLeft", moveLeft);

        storeGameKey();
    }

    public void storeGameMoveToFloorKey(int moveToFloor) {
        this.gameKey.put("moveToFloor", moveToFloor);

        storeGameKey();
    }

    public void storeGameRotateKey(int rotate) {
        this.gameKey.put("rotate", rotate);

        storeGameKey();
    }

    private void storeGameKey() {
        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(PATH);
            JSONObject setting = (JSONObject) jsonParser.parse(reader);

            setting.put("gameKey", this.gameKey);

            FileWriter writer = new FileWriter(PATH);
            writer.write(setting.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void setBlindnessMode(int blindnessMode) {
        this.blindnessMode = blindnessMode;

        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(PATH);
            JSONObject setting = (JSONObject) jsonParser.parse(reader);

            setting.put("blindnessMode", this.blindnessMode);

            FileWriter writer = new FileWriter(PATH);
            writer.write(setting.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    // Setting Multi mode game key

    public void storeMultiModeMoveDownKey(int moveDown, Player player) {
        if (player == Player.FIRST) {
            Set<String> keySet = playerSecondKey.keySet();
            for (String key : keySet) {
                if (Objects.equals(playerSecondKey.get(key), playerFirstKey.get("moveDown"))) {
                    return;
                }
            }
            this.playerFirstKey.put("moveDown", moveDown);
        } else if (player == Player.SECOND) {
            Set<String> keySet = playerFirstKey.keySet();
            for (String key : keySet) {
                if (Objects.equals(playerFirstKey.get(key), playerSecondKey.get("moveDown"))) {
                    return;
                }
            }
            this.playerSecondKey.put("moveDown", moveDown);
        }

        storeMultiModeKey(player);
    }

    public void storeMultiModeMoveRightKey(int moveRight, Player player) {
        if (player == Player.FIRST) {
            Set<String> keySet = playerSecondKey.keySet();
            for (String key : keySet) {
                if (Objects.equals(playerSecondKey.get(key), playerFirstKey.get("moveRight"))) {
                    return;
                }
            }
            this.playerFirstKey.put("moveRight", moveRight);
        } else if (player == Player.SECOND) {
            Set<String> keySet = playerFirstKey.keySet();
            for (String key : keySet) {
                if (Objects.equals(playerFirstKey.get(key), playerSecondKey.get("moveRight"))) {
                    return;
                }
            }
            this.playerSecondKey.put("moveRight", moveRight);
        }

        storeMultiModeKey(player);
    }

    public void storeMultiModeMoveLeftKey(int moveLeft, Player player) {
        if (player == Player.FIRST) {
            Set<String> keySet = playerSecondKey.keySet();
            for (String key : keySet) {
                if (Objects.equals(playerSecondKey.get(key), playerFirstKey.get("moveLeft"))) {
                    return;
                }
            }
            this.playerFirstKey.put("moveLeft", moveLeft);
        } else if (player == Player.SECOND) {
            Set<String> keySet = playerFirstKey.keySet();
            for (String key : keySet) {
                if (Objects.equals(playerFirstKey.get(key), playerSecondKey.get("moveLeft"))) {
                    return;
                }
            }
            this.playerSecondKey.put("moveLeft", moveLeft);
        }

        storeMultiModeKey(player);
    }

    public void storeMultiModeMoveToFloorKey(int moveToFloor, Player player) {
        if (player == Player.FIRST) {
            Set<String> keySet = playerSecondKey.keySet();
            for (String key : keySet) {
                if (Objects.equals(playerSecondKey.get(key), playerFirstKey.get("moveToFloor"))) {
                    return;
                }
            }
            this.playerFirstKey.put("moveToFloor", moveToFloor);
        } else if (player == Player.SECOND) {
            Set<String> keySet = playerFirstKey.keySet();
            for (String key : keySet) {
                if (Objects.equals(playerFirstKey.get(key), playerSecondKey.get("moveToFloor"))) {
                    return;
                }
            }
            this.playerSecondKey.put("moveToFloor", moveToFloor);
        }

        storeMultiModeKey(player);
    }

    public void storeMultiModeRotateKey(int rotate, Player player) {
        if (player == Player.FIRST) {
            Set<String> keySet = playerSecondKey.keySet();
            for (String key : keySet) {
                if (Objects.equals(playerSecondKey.get(key), playerFirstKey.get("rotate"))) {
                    return;
                }
            }
            this.playerFirstKey.put("rotate", rotate);
        } else if (player == Player.SECOND) {
            Set<String> keySet = playerFirstKey.keySet();
            for (String key : keySet) {
                if (Objects.equals(playerFirstKey.get(key), playerSecondKey.get("rotate"))) {
                    return;
                }
            }
            this.playerSecondKey.put("rotate", rotate);
        }

        storeMultiModeKey(player);
    }

    public void storeMultiModeKey(Player player) {
        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(PATH);
            JSONObject setting = (JSONObject) jsonParser.parse(reader);

            if (player == Player.FIRST) {
                setting.put(String.format("player%dKey", player.getValue()), this.playerFirstKey);
            } else if (player == Player.SECOND) {
                setting.put(String.format("player%dKey", player.getValue()), this.playerSecondKey);
            }

            FileWriter writer = new FileWriter(PATH);
            writer.write(setting.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return Integer.parseInt(String.valueOf(windowSize.get("width")));
    }

    public int getHeight() {
        return Integer.parseInt(String.valueOf(windowSize.get("width")));
    }

    public int getGameMoveDown() {
        return Integer.parseInt(String.valueOf(gameKey.get("moveDown")));
    }

    public int getGameMoveRight() {
        return Integer.parseInt(String.valueOf(gameKey.get("moveRight")));
    }

    public int getGameMoveLeft() {
        return Integer.parseInt(String.valueOf(gameKey.get("moveLeft")));
    }

    public int getGameMoveToFloor() {
        return Integer.parseInt(String.valueOf(gameKey.get("moveToFloor")));
    }

    public int getRotateKey() {
        return Integer.parseInt(String.valueOf(gameKey.get("rotate")));
    }

    public int getPauseKey() {
        return Integer.parseInt(String.valueOf(gameKey.get("pause")));
    }

    public int getBlindnessMode() {
        return blindnessMode;
    }

    public int getPlayerFirstMoveDown() {
        return Integer.parseInt(String.valueOf(playerFirstKey.get("moveDown")));
    }

    public int getPlayerFirstMoveRight() {
        return Integer.parseInt(String.valueOf(playerFirstKey.get("moveRight")));
    }

    public int getPlayerFirstMoveLeft() {
        return Integer.parseInt(String.valueOf(playerFirstKey.get("moveLeft")));
    }

    public int getPlayerFirstMoveToFloor() {
        return Integer.parseInt(String.valueOf(playerFirstKey.get("moveToFloor")));
    }

    public int getPlayerFirstRotate() {
        return Integer.parseInt(String.valueOf(playerFirstKey.get("rotate")));
    }

    public int getPlayerSecondMoveDown() {
        return Integer.parseInt(String.valueOf(playerSecondKey.get("moveDown")));
    }

    public int getPlayerSecondMoveRight() {
        return Integer.parseInt(String.valueOf(playerSecondKey.get("moveRight")));
    }

    public int getPlayerSecondMoveLeft() {
        return Integer.parseInt(String.valueOf(playerSecondKey.get("moveLeft")));
    }

    public int getPlayerSecondMoveToFloor() {
        return Integer.parseInt(String.valueOf(playerSecondKey.get("moveToFloor")));
    }

    public int getPlayerSecondRotate() {
        return Integer.parseInt(String.valueOf(playerSecondKey.get("rotate")));
    }

    public void resetSetting() {
        DefaultSetting defaultSetting = DefaultSetting.getInstance();
        try {
            JSONObject setting = new JSONObject();

            setting.put("windowSize", new JSONObject(defaultSetting.getWindowSize()));
            setting.put("gameKey", new JSONObject(defaultSetting.getGameKeyMap()));
            setting.put("blindnessMode", defaultSetting.getBlindnessMode());
            setting.put("player1Key", new JSONObject(defaultSetting.getPlayerFirstKeyMap()));
            setting.put("player2Key", new JSONObject(defaultSetting.getPlayerSecondKeyMap()));

            this.windowSize = defaultSetting.getWindowSize();
            this.gameKey = defaultSetting.getGameKeyMap();
            this.blindnessMode = defaultSetting.getBlindnessMode();
            this.playerFirstKey = defaultSetting.getPlayerFirstKeyMap();
            this.playerSecondKey = defaultSetting.getPlayerSecondKeyMap();

            FileWriter writer = new FileWriter(PATH);
            writer.write(setting.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
