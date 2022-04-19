package data.setting;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class SettingData {
    private static SettingData uniqueInstance = null;

    private HashMap<String, Integer> windowSize;
    private HashMap<String, Integer> menuKey;
    private HashMap<String, Integer> gameKey;
    private boolean isBlindnessMode;
    private int typeOfBlindness;

    private final String PATH = "/Users/jeongjin/IdeaProjects/se-team2-tetris/database/Setting.json";


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
//                기존에 존재할 때만 여기에 들어올까?
                System.out.println("File already exists.");

                JSONParser jsonParser = new JSONParser();

                FileReader reader = new FileReader(PATH);
                JSONObject obj = (JSONObject) jsonParser.parse(reader);

                Object windowSizeMap = obj.get("windowSize");
                Object menuKeyMap = obj.get("menuKey");
                Object gameKeyMap = obj.get("gameKey");
                this.windowSize = (HashMap<String, Integer>) windowSizeMap;
                this.menuKey = (HashMap<String, Integer>) menuKeyMap;
                this.gameKey = (HashMap<String, Integer>) gameKeyMap;
                this.isBlindnessMode = (boolean) obj.get("isBlindnessMode");
                this.typeOfBlindness = Integer.parseInt(String.valueOf(obj.get("typeOfBlindness")));
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

    public void storeMenuKey(int moveUp, int moveDown, int selectMenu) {
        this.menuKey.put("moveUp", moveUp);
        this.menuKey.put("moveDown", moveDown);
        this.menuKey.put("selectMenu", selectMenu);

        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(PATH);
            JSONObject setting = (JSONObject) jsonParser.parse(reader);

            setting.put("menuKey", this.menuKey);

            FileWriter writer = new FileWriter(PATH);
            writer.write(setting.toJSONString());
            writer.flush();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public void storeGameKey(int moveDown, int moveRight, int moveLeft, int moveToFloor, int rotate, int pause) {
        this.gameKey.put("moveDown", moveDown);
        this.gameKey.put("moveRight", moveRight);
        this.gameKey.put("moveLeft", moveLeft);
        this.gameKey.put("moveToFloor", moveToFloor);
        this.gameKey.put("rotate", rotate);
        this.gameKey.put("pause", pause);

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

    public void setIsBlindnessMode(boolean isBlindnessMode) {
        this.isBlindnessMode = isBlindnessMode;

        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(PATH);
            JSONObject setting = (JSONObject) jsonParser.parse(reader);

            setting.put("isBlindnessMode", this.isBlindnessMode);

            FileWriter writer = new FileWriter(PATH);
            writer.write(setting.toJSONString());
            writer.flush();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void setTypeOfBlindness(int typeOfBlindness) {
        this.typeOfBlindness = typeOfBlindness;

        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(PATH);
            JSONObject setting = (JSONObject) jsonParser.parse(reader);

            setting.put("typeOfBlindness", this.typeOfBlindness);

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

    public int getMenuMoveUp() {
        return Integer.parseInt(String.valueOf(menuKey.get("moveUp")));
    }

    public int getMenuMoveDown() {
        return Integer.parseInt(String.valueOf(menuKey.get("moveDown")));
    }

    public int getSelectMenu() {
        return Integer.parseInt(String.valueOf(menuKey.get("selectMenu")));
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

    public boolean isBlindnessMode() {
        return isBlindnessMode;
    }

    public int getTypeOfBlindness() {
        return typeOfBlindness;
    }

    public void resetSetting() throws IOException {
        DefaultSetting defaultSetting = DefaultSetting.getInstance();
        JSONObject setting = new JSONObject();

        setting.put("windowSize", new JSONObject(defaultSetting.getWindowSize()));
        setting.put("menuKey", new JSONObject(defaultSetting.getMenuKeyMap()));
        setting.put("gameKey", new JSONObject(defaultSetting.getGameKeyMap()));
        setting.put("isBlindnessMode", defaultSetting.isBlindnessMode());
        setting.put("typeOfBlindness", defaultSetting.getTypeOfBlindness());

        this.windowSize = defaultSetting.getWindowSize();
        this.menuKey = defaultSetting.getMenuKeyMap();
        this.gameKey = defaultSetting.getGameKeyMap();
        this.isBlindnessMode = defaultSetting.isBlindnessMode();
        this.typeOfBlindness = defaultSetting.getTypeOfBlindness();

        FileWriter writer = new FileWriter(PATH);
        writer.write(setting.toJSONString());
        writer.flush();
    }
}
