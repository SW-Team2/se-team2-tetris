package data.score;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ScoreBoardDataTest {
    final String DEFAULT_MODE_SCORE_PATH = System.getProperty("user.dir") + "/database/DefaultModeScoreTest.json";
    final String ITEM_MODE_SCORE_PATH = System.getProperty("user.dir") + "/database/ItemModeScoreTest.json";
    File defaultScoreFile, ItemScoreFile;

    ArrayList<HashMap<String, Object>> defaultModeScores;
    ArrayList<HashMap<String, Object>> itemModeScores;

    @BeforeAll
    void setUp() throws IOException {
        defaultScoreFile = new File(DEFAULT_MODE_SCORE_PATH);
        ItemScoreFile = new File(ITEM_MODE_SCORE_PATH);
        defaultScoreFile.createNewFile();
        ItemScoreFile.createNewFile();

        JSONArray emptyList = new JSONArray();

        FileWriter defaultWriter = new FileWriter(DEFAULT_MODE_SCORE_PATH);
        FileWriter itemWriter = new FileWriter(ITEM_MODE_SCORE_PATH);

        defaultWriter.write(emptyList.toJSONString());
        itemWriter.write(emptyList.toJSONString());

        defaultWriter.flush();
        itemWriter.flush();
    }

    @AfterAll
    void tearDown() {
        defaultScoreFile.delete();
        ItemScoreFile.delete();
    }

    static Stream<Score> provideDefaultScore() {
        return Stream.of(
                new Score("jj", 1, Difficulty.HARD.getValue(), Mode.DEFAULT.getValue()),
                new Score("sy", 2, Difficulty.HARD.getValue(), Mode.DEFAULT.getValue()),
                new Score("jj", 3, Difficulty.HARD.getValue(), Mode.DEFAULT.getValue()),
                new Score("sy", 4, Difficulty.HARD.getValue(), Mode.DEFAULT.getValue()),
                new Score("jj", 5, Difficulty.HARD.getValue(), Mode.DEFAULT.getValue()),
                new Score("sy", 6, Difficulty.HARD.getValue(), Mode.DEFAULT.getValue()),
                new Score("jj", 7, Difficulty.HARD.getValue(), Mode.DEFAULT.getValue()),
                new Score("sy", 8, Difficulty.HARD.getValue(), Mode.DEFAULT.getValue()),
                new Score("jj", 9, Difficulty.HARD.getValue(), Mode.DEFAULT.getValue()),
                new Score("sy", 10, Difficulty.HARD.getValue(), Mode.DEFAULT.getValue()),
                new Score("jj", 11, Difficulty.HARD.getValue(), Mode.DEFAULT.getValue()),
                new Score("sy", 12, Difficulty.HARD.getValue(), Mode.DEFAULT.getValue())
        );
    }

    @ParameterizedTest
    @MethodSource("provideDefaultScore")
    void addDefaultModeScore(Score dummy) {
        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(DEFAULT_MODE_SCORE_PATH);
            JSONArray defaultScores = (JSONArray) jsonParser.parse(reader);

            HashMap<String, Object> score = new HashMap<>();
            score.put("name", dummy.getName());
            score.put("score", dummy.getScore());
            score.put("difficulty", dummy.getDifficulty());
            score.put("gameMode", dummy.getGameMode());

            defaultScores.add(new JSONObject(score));

            FileWriter writer = new FileWriter(DEFAULT_MODE_SCORE_PATH);
            writer.write(defaultScores.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled
    void addItemModeScore() {
        Score dummy = new Score("jj", 123, Difficulty.NORMAL.getValue(), Mode.ITEM_MODE.getValue());
        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(ITEM_MODE_SCORE_PATH);
            JSONArray defaultScores = (JSONArray) jsonParser.parse(reader);

            HashMap<String, Object> score = new HashMap<>();
            score.put("name", dummy.getName());
            score.put("score", dummy.getScore());
            score.put("difficulty", dummy.getDifficulty());
            score.put("gameMode", dummy.getGameMode());

            JSONObject obj = new JSONObject(score);

            defaultScores.add(obj);

            FileWriter writer = new FileWriter(ITEM_MODE_SCORE_PATH);
            writer.write(defaultScores.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled
    void getDefaultModeScore() {
        readDefaultModeScores();

        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(DEFAULT_MODE_SCORE_PATH);
            JSONArray testList = (JSONArray) jsonParser.parse(reader);

//            for (int i = 0; i < testList.size(); i++) {
//                JSONObject test = (JSONObject) testList.get(i);
//                HashMap<String, Object> score = this.defaultModeScores.get(i);
//
//                assertThat(test.get("name")).isEqualTo(score.get("name"));
//                assertThat(Integer.parseInt(String.valueOf(test.get("score")))).isEqualTo(score.get("score"));
//                assertThat(test.get("difficulty")).isEqualTo(score.get("difficulty"));
//                assertThat(test.get("gameMode")).isEqualTo(score.get("gameMode"));
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled
    void getItemModeScore() {

    }

    @Test
    @Disabled
    void resetScoreBoard() {
        readItemModeScores();
    }

    void readDefaultModeScores() {
        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(DEFAULT_MODE_SCORE_PATH);
            JSONArray defaultScores = (JSONArray) jsonParser.parse(reader);
            System.out.println(defaultScores);

            if (defaultScores != null) {
                for (int i = 0; i < defaultScores.size(); i++) {
                    JSONObject score = (JSONObject) defaultScores.get(i);
                    this.defaultModeScores.add(score);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    void readItemModeScores() {
        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(ITEM_MODE_SCORE_PATH);
            JSONArray itemScores = (JSONArray) jsonParser.parse(reader);

            if (itemScores != null) {
                for (int i = 0; i < itemScores.size(); i++) {
                    JSONObject score = (JSONObject) itemScores.get(i);
                    this.itemModeScores.add(score);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}