package data.score;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ScoreBoardData {
    private static ScoreBoardData uniqueInstance = null;

    ArrayList<HashMap<String, Object>> defaultModeScores;
    ArrayList<HashMap<String, Object>> itemModeScores;

    private final int NUMBER_OF_SCORES = 10;
    private final String DEFAULT_MODE_SCORE_PATH = System.getProperty("user.dir") + "/database/DefaultModeScore.json";
    private final String ITEM_MODE_SCORE_PATH = System.getProperty("user.dir") + "/database/ItemModeScore.json";

    private ScoreBoardData() {
        initializeDefaultData();
        initializeItemScore();
    }

    public static ScoreBoardData getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ScoreBoardData();
        }
        return uniqueInstance;
    }

    public void initializeDefaultData() {
        File file = new File(DEFAULT_MODE_SCORE_PATH);
        this.defaultModeScores = new ArrayList<>();

        try {
            if (file.createNewFile()) {
                JSONArray emptyList = new JSONArray();

                FileWriter writer = new FileWriter(DEFAULT_MODE_SCORE_PATH);
                writer.write(emptyList.toJSONString());
                writer.flush();
            } else {
                System.out.println("File already exists.");
                readDefaultModeScores();
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void initializeItemScore() {
        File file = new File(ITEM_MODE_SCORE_PATH);
        this.itemModeScores = new ArrayList<>();

        try {
            if (file.createNewFile()) {
                JSONArray emptyList = new JSONArray();

                FileWriter writer = new FileWriter(ITEM_MODE_SCORE_PATH);
                writer.write(emptyList.toJSONString());
                writer.flush();
            } else {
                System.out.println("File already exists.");
                readItemModeScores();
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public ArrayList<HashMap<String, Object>> getDefaultModeScore() {
        File file = new File(DEFAULT_MODE_SCORE_PATH);
        if (!file.exists())
            return new ArrayList<>();
        readDefaultModeScores();
        return this.defaultModeScores;
    }

    public ArrayList<HashMap<String, Object>> getItemModeScore() {
        File file = new File(ITEM_MODE_SCORE_PATH);
        if (!file.exists())
            return new ArrayList<>();
        readItemModeScores();
        return this.itemModeScores;
    }

    public void addDefaultModeScore(Score newValue) {
        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(DEFAULT_MODE_SCORE_PATH);
            JSONArray defaultScores = (JSONArray) jsonParser.parse(reader);

            HashMap<String, Object> score = new HashMap<>();
            score.put("name", newValue.getName());
            score.put("score", newValue.getScore());
            score.put("difficulty", newValue.getDifficulty());
            score.put("gameMode", newValue.getGameMode());

            defaultScores.add(new JSONObject(score));

            FileWriter writer = new FileWriter(DEFAULT_MODE_SCORE_PATH);
            writer.write(defaultScores.toJSONString());
            writer.flush();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void addItemModeScore(Score newValue) {
        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(ITEM_MODE_SCORE_PATH);
            JSONArray itemScores = (JSONArray) jsonParser.parse(reader);

            HashMap<String, Object> score = new HashMap<>();
            score.put("name", newValue.getName());
            score.put("score", newValue.getScore());
            score.put("difficulty", newValue.getDifficulty());
            score.put("gameMode", newValue.getGameMode());

            itemScores.add(new JSONObject(score));

            FileWriter writer = new FileWriter(ITEM_MODE_SCORE_PATH);
            writer.write(itemScores.toJSONString());
            writer.flush();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void resetScoreBoard() {
        this.defaultModeScores = new ArrayList<>();
        this.itemModeScores = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try {
            FileReader defaultScoreReader = new FileReader(DEFAULT_MODE_SCORE_PATH);
            FileReader itemScoreReader = new FileReader(ITEM_MODE_SCORE_PATH);

            JSONArray defaultScores = (JSONArray) jsonParser.parse(defaultScoreReader);
            JSONArray itemScores = (JSONArray) jsonParser.parse(itemScoreReader);
            for (int i = 0; i < defaultScores.size(); i++)
                defaultScores.remove(i);

            for (int i = 0; i < itemScores.size(); i++)
                itemScores.remove(i);

            FileWriter defaultScoreWriter = new FileWriter(DEFAULT_MODE_SCORE_PATH);
            FileWriter itemScoreWriter = new FileWriter(ITEM_MODE_SCORE_PATH);

            defaultScoreWriter.write(defaultScores.toJSONString());
            itemScoreWriter.write(itemScores.toJSONString());

            defaultScoreWriter.flush();
            itemScoreWriter.flush();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void readDefaultModeScores() {
        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(DEFAULT_MODE_SCORE_PATH);
            JSONArray defaultScores = (JSONArray) jsonParser.parse(reader);

            if (defaultScores != null) {
                Collections.sort(defaultScores, comparator);
                for (int i = 0; i < NUMBER_OF_SCORES && i < defaultScores.size(); i++) {
                    JSONObject score = (JSONObject) defaultScores.get(i);
                    this.defaultModeScores.add(score);
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void readItemModeScores() {
        try {
            JSONParser jsonParser = new JSONParser();

            FileReader reader = new FileReader(ITEM_MODE_SCORE_PATH);
            JSONArray itemScores = (JSONArray) jsonParser.parse(reader);

            if (itemScores != null) {
                Collections.sort(itemScores, comparator);
                for (int i = 0; i < NUMBER_OF_SCORES && i < itemScores.size(); i++) {
                    JSONObject score = (JSONObject) itemScores.get(i);
                    this.itemModeScores.add(score);
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Comparator<HashMap<String, Object>> comparator = (n1, n2) -> {
        Integer a = Integer.parseInt(String.valueOf(n1.get("score")));
        Integer b = Integer.parseInt(String.valueOf(n2.get("score")));
        return Integer.compare(b, a);
    };
}
