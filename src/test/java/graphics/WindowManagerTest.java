package graphics;

import data.setting.SettingData;
import org.junit.Test;

import tetrisgame.util.ImageCVDConverter;
import tetrisgame.util.ImageLoader;

import java.nio.Buffer;

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
            win.showMultiKeySetting();
            win.showMultiModeSelect();
            win.showTimeLimitGame();
            win.showTimeLimitGame();
            win.showSetting();
            win.showScreenSize();
            win.showColorBlind();
            win.showKeySetting();
            win.showScore();
            win.paint();
        } catch (Exception e) {
        }

        SettingData.getInstance().setBlindnessMode(1);
        win.refreshSetting();
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
            win.paint();
        } catch (Exception e) {
        }

        SettingData.getInstance().setBlindnessMode(2);
        win.refreshSetting();
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
            win.paint();
        } catch (Exception e) {
        }

        ImageLoader.Load();
        ImageCVDConverter cvd = new ImageCVDConverter();
        ImageCVDConverter.convert(ImageLoader.getInstance().getTexture("tile_white"), 0);
        ImageCVDConverter.convert(ImageLoader.getInstance().getTexture("tile_white"), 1);
        ImageCVDConverter.convert(ImageLoader.getInstance().getTexture("tile_white"), 2);
    }
}
