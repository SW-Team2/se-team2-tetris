package graphics;

import org.junit.Test;
import tetrisgame.util.ImageLoader;

import static org.junit.jupiter.api.Assertions.*;

public class WindowManagerTest {
    @Test
    public void graphicsTest() {

        ImageLoader.Load();

        WindowManager win = new WindowManager();

        try {
            win.showMain();
            win.showDifficulty();
            win.showGame();
            win.showItemGame();
            win.showMultiGame();
            win.showItemMultiGame();
            win.showTimeLimitGame();
            win.showSetting();
            win.showScreenSize();
            win.showColorBlind();
            win.showKeySetting();
            win.showScore();
        } catch (Exception e) {
            assert (false);
        }
    }
}