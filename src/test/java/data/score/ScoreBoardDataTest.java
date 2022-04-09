package data.score;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardDataTest {
    ScoreBoardData scoreData = ScoreBoardData.getInstance();


    @Test
    void getScoreDataList() {
        for(Score s : scoreData.getScoreDataList())
            System.out.println(s);
    }

    @Test
    @ParameterizedTest(name = "{0}, {1}, {2}, {3}")
    @CsvSource({
            "\"jj\", 123, \"Easy\", 1",
    })
    void addNewScore(String name, int score, String difficulty, int isItemMode) {

    }
}