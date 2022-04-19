package data.setting;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class SettingData {
    private static SettingData uniqueInstance = null;

    private HashMap<String, Integer> windowSize;
    private HashMap<String, Integer> gameKey;
    private int blindnessMode;

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
                System.out.println("File already exists.");

                JSONParser jsonParser = new JSONParser();

                FileReader reader = new FileReader(PATH);
                JSONObject obj = (JSONObject) jsonParser.parse(reader);

                Object windowSizeMap = obj.get("windowSize");
                Object gameKeyMap = obj.get("gameKey");
                this.windowSize = (HashMap<String, Integer>) windowSizeMap;
                this.gameKey = (HashMap<String, Integer>) gameKeyMap;
                this.blindnessMode = Integer.parseInt(String.valueOf(obj.get("blindnessMode")));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
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

    public void storeGamePauseKey(int pause) {
        this.gameKey.put("pause", pause);

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

    public void resetSetting() throws IOException {
        DefaultSetting defaultSetting = DefaultSetting.getInstance();
        JSONObject setting = new JSONObject();

        setting.put("windowSize", new JSONObject(defaultSetting.getWindowSize()));
        setting.put("gameKey", new JSONObject(defaultSetting.getGameKeyMap()));
        setting.put("blindnessMode", defaultSetting.getBlindnessMode());

        this.windowSize = defaultSetting.getWindowSize();
        this.gameKey = defaultSetting.getGameKeyMap();
        this.blindnessMode = defaultSetting.getBlindnessMode();

        FileWriter writer = new FileWriter(PATH);
        writer.write(setting.toJSONString());
        writer.flush();
    }
}
