package data.setting;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.event.KeyEvent;
import java.io.*;
import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SettingDataTest {
    final String PATH = System.getProperty("user.dir") + "/database/SettingTest.json";
    File file;

    HashMap<String, Integer> windowSize;
    HashMap<String, Integer> gameKey;
    int blindnessMode;
    DefaultSetting defaultSetting;

    @BeforeAll
    void setUp() throws IOException {
        file = new File(PATH);
        file.createNewFile();

        defaultSetting = DefaultSetting.getInstance();
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

    @AfterAll
    void tearDown() {
        file.delete();
    }

    @ParameterizedTest
    @CsvSource({"100, 100", "200, 300"})
    void storeWindowSize(int width, int height) {
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

        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(PATH);
            JSONObject setting = (JSONObject) jsonParser.parse(reader);
            JSONObject obj = (JSONObject) setting.get("windowSize");

            Integer widthTest = Integer.parseInt(String.valueOf(obj.get("width")));
            Integer heightTest = Integer.parseInt(String.valueOf(obj.get("height")));
            assertThat(widthTest).isEqualTo(width);
            assertThat(widthTest).isEqualTo(this.windowSize.get("width"));
            assertThat(heightTest).isEqualTo(height);
            assertThat(heightTest).isEqualTo(this.windowSize.get("height"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {KeyEvent.VK_S, KeyEvent.VK_DOWN})
    void storeGameMoveDownKey(int moveDown) {
        this.gameKey.put("moveDown", moveDown);

        storeGameKey();
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(PATH);
            JSONObject setting = (JSONObject) jsonParser.parse(reader);
            JSONObject obj = (JSONObject) setting.get("gameKey");

            Integer test = Integer.parseInt(String.valueOf(obj.get("moveDown")));
            assertThat(test).isEqualTo(moveDown);
            assertThat(test).isEqualTo(this.gameKey.get("moveDown"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {KeyEvent.VK_D, KeyEvent.VK_RIGHT})
    void storeGameMoveRightKey(int moveRight) {
        this.gameKey.put("moveRight", moveRight);
        storeGameKey();
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(PATH);
            JSONObject setting = (JSONObject) jsonParser.parse(reader);
            JSONObject obj = (JSONObject) setting.get("gameKey");

            Integer test = Integer.parseInt(String.valueOf(obj.get("moveRight")));
            assertThat(test).isEqualTo(moveRight);
            assertThat(test).isEqualTo(this.gameKey.get("moveRight"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {KeyEvent.VK_A, KeyEvent.VK_LEFT})
    void storeGameMoveLeftKey(int moveLeft) {
        this.gameKey.put("moveLeft", moveLeft);
        storeGameKey();
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(PATH);
            JSONObject setting = (JSONObject) jsonParser.parse(reader);
            JSONObject obj = (JSONObject) setting.get("gameKey");

            Integer test = Integer.parseInt(String.valueOf(obj.get("moveLeft")));
            assertThat(test).isEqualTo(moveLeft);
            assertThat(test).isEqualTo(this.gameKey.get("moveLeft"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {KeyEvent.VK_W, KeyEvent.VK_UP})
    void storeGameMoveToFloorKey(int moveToFloor) {
        this.gameKey.put("moveToFloor", moveToFloor);
        storeGameKey();
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(PATH);
            JSONObject setting = (JSONObject) jsonParser.parse(reader);
            JSONObject obj = (JSONObject) setting.get("gameKey");

            Integer test = Integer.parseInt(String.valueOf(obj.get("moveToFloor")));
            assertThat(test).isEqualTo(moveToFloor);
            assertThat(test).isEqualTo(this.gameKey.get("moveToFloor"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {KeyEvent.VK_SPACE, KeyEvent.VK_T})
    void storeGameRotateKey(int rotate) {
        this.gameKey.put("rotate", rotate);
        storeGameKey();
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(PATH);
            JSONObject setting = (JSONObject) jsonParser.parse(reader);
            JSONObject obj = (JSONObject) setting.get("gameKey");

            Integer test = Integer.parseInt(String.valueOf(obj.get("rotate")));

            assertThat(test).isEqualTo(rotate);
            assertThat(test).isEqualTo(this.gameKey.get("rotate"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {KeyEvent.VK_ESCAPE, KeyEvent.VK_P})
    void storeGamePauseKey(int pause) {
        this.gameKey.put("pause", pause);
        storeGameKey();
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(PATH);
            JSONObject setting = (JSONObject) jsonParser.parse(reader);
            JSONObject obj = (JSONObject) setting.get("gameKey");

            Integer test = Integer.parseInt(String.valueOf(obj.get("pause")));

            assertThat(test).isEqualTo(pause);
            assertThat(test).isEqualTo(this.gameKey.get("pause"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    void storeGameKey() {
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

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2 })
    void setBlindnessMode(int blindnessMode) {
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

        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(PATH);
            JSONObject setting = (JSONObject) jsonParser.parse(reader);

            Integer modeTest = Integer.parseInt(String.valueOf(setting.get("blindnessMode")));
            assertThat(modeTest).isEqualTo(modeTest);
            assertThat(modeTest).isEqualTo(this.blindnessMode);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void resetSetting() throws IOException, ParseException {
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


        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(PATH);
        JSONObject settingTest = (JSONObject) jsonParser.parse(reader);

        assertThat(this.windowSize).isEqualTo(defaultSetting.getWindowSize());
        assertThat(this.gameKey).isEqualTo(defaultSetting.getGameKeyMap());
        assertThat(this.blindnessMode).isEqualTo(defaultSetting.getBlindnessMode());
    }
}