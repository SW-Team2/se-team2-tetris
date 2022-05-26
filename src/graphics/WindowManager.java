package graphics;

import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import data.setting.SettingData;
import graphics.screens.*;

public class WindowManager {
    private JFrame mWindow;
    private CardLayout mCards;
    private MainMenuScreen mMain;
    private DifficultyScreen mDifficulty;
    private GameScreen mGame;
    private ItemModeGameScreen mItemGame;
    private MultiPlayingScreen mMultiGame;
    private ItemModeMultiPlayingScreen mItemMultiGame;
    private TimeLimitMultiPlayingScreen mTimeLimitGame;
    private MultiModeSelectScreen mMultiModeSelect;
    private SettingMenuScreen mSetting;
    private ScreenSizeScreen mScreenSize;
    private ColorBlindModeScreen mColorBlindMode;
    private KeySettingScreen mKeySetting;
    private MultiKeySettingScreen mMultiKeySetting;
    private ScoreBoardScreen mScore;
    private eScreenInfo meCurrScreen;

    private boolean mbMulti;

    static final String BAR = new String("Bar");

    public class MsgManager implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            eScreenInfo switchScreenTo = eScreenInfo.NONE;
            switch (meCurrScreen) {
                case MAIN:
                    switchScreenTo = mMain.getUserInput(e);
                    break;
                case DIFFICULTY:
                    switchScreenTo = mDifficulty.getUserInput(e);
                    break;
                case GAME:
                    switchScreenTo = mGame.getUserInput(e);
                    break;
                case ITEMGAME:
                    switchScreenTo = mItemGame.getUserInput(e);
                    break;
                case MULTIGAME:
                    switchScreenTo = mMultiGame.getUserInput(e);
                    break;
                case ITEMMULT:
                    switchScreenTo = mItemMultiGame.getUserInput(e);
                    break;
                case TIMELIMIT:
                    switchScreenTo = mTimeLimitGame.getUserInput(e);
                    break;
                case SETTING:
                    switchScreenTo = mSetting.getUserInput(e);
                    break;
                case MULTI_MODE_SELECT:
                    switchScreenTo = mMultiModeSelect.getUserInput(e);
                    break;
                case SET_SCREEN_SIZE:
                    switchScreenTo = mScreenSize.getUserInput(e);
                    break;
                case SET_COLOR_BLIND_MODE:
                    switchScreenTo = mColorBlindMode.getUserInput(e);
                    break;
                case SET_GAME_KEY:
                    switchScreenTo = mKeySetting.getUserInput(e);
                    break;
                case SET_MULTI_GAME_KEY:
                    switchScreenTo = mMultiKeySetting.getUserInput(e);
                    break;
                case SCOREBOARD:
                    switchScreenTo = mScore.getUserInput(e);
                    break;
                default:
                    assert (false) : "Undefined eScreenInfo";
                    break;
            }
            switch (switchScreenTo) {
                case NONE:
                    break;
                case MAIN:
                    showMain();
                    break;
                case DIFFICULTY:
                    showDifficulty();
                    break;
                case GAME:
                    showGame();
                    break;
                case ITEMGAME:
                    showItemGame();
                    break;
                case MULTIGAME:
                    showMultiGame();
                    break;
                case ITEMMULT:
                    showItemMultiGame();
                    break;
                case TIMELIMIT:
                    showTimeLimitGame();
                    break;
                case MULTI_MODE_SELECT:
                    showMultiModeSelect();
                    break;
                case SETTING:
                    showSetting();
                    break;
                case SCOREBOARD:
                    showScore();
                    break;
                case SET_SCREEN_SIZE:
                    showScreenSize();
                    break;
                case SET_GAME_KEY:
                    showKeySetting();
                    break;
                case SET_MULTI_GAME_KEY:
                    showMultiKeySetting();
                    break;
                case SET_COLOR_BLIND_MODE:
                    showColorBlind();
                    break;
                case EXIT:
                    if (JOptionPane.showConfirmDialog(mWindow,
                            "Are you sure you want to close this window?", "Close Window?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                    break;
                default:
                    assert (false) : "Undefined eScreenInfo";
                    break;

            }
            refreshSetting();
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

    }

    public WindowManager() {
        mWindow = new JFrame();
        mWindow.setResizable(false);

        refreshSetting();
        mWindow.setLocation(200, 200);
        mCards = new CardLayout(0, 0);
        mWindow.getContentPane().setLayout(mCards);

        mWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mMain = new MainMenuScreen();
        mDifficulty = new DifficultyScreen();
        mGame = new GameScreen();
        mItemGame = new ItemModeGameScreen();
        mMultiGame = new MultiPlayingScreen();
        mItemMultiGame = new ItemModeMultiPlayingScreen();
        mTimeLimitGame = new TimeLimitMultiPlayingScreen();
        mMultiModeSelect = new MultiModeSelectScreen();
        mSetting = new SettingMenuScreen();
        mScreenSize = new ScreenSizeScreen();
        mColorBlindMode = new ColorBlindModeScreen();
        mKeySetting = new KeySettingScreen();
        mMultiKeySetting = new MultiKeySettingScreen();
        mScore = new ScoreBoardScreen();
        mWindow.getContentPane().add("main", mMain);
        mWindow.getContentPane().add("difficulty", mDifficulty);
        mWindow.getContentPane().add("game", mGame);
        mWindow.getContentPane().add("itemgame", mItemGame);
        mWindow.getContentPane().add("multigame", mMultiGame);
        mWindow.getContentPane().add("itemmultigame", mItemMultiGame);
        mWindow.getContentPane().add("timelimitgame", mTimeLimitGame);
        mWindow.getContentPane().add("multimode", mMultiModeSelect);
        mWindow.getContentPane().add("setting", mSetting);
        mWindow.getContentPane().add("screensize", mScreenSize);
        mWindow.getContentPane().add("colorblind", mColorBlindMode);
        mWindow.getContentPane().add("keysetting", mKeySetting);
        mWindow.getContentPane().add("multikeysetting", mMultiKeySetting);
        mWindow.getContentPane().add("score", mScore);

        meCurrScreen = eScreenInfo.MAIN;
        mWindow.addKeyListener(new MsgManager());
        mWindow.setFocusable(true);

        this.showMain();
        mWindow.setVisible(true);
    }

    public void refreshSetting() {
        int width = SettingData.getInstance().getWidth();
        if (mbMulti) {
            width *= 2;
        }
        int height = SettingData.getInstance().getHeight();
        mWindow.setSize(width, height);
    }

    public void showMain() {
        mbMulti = false;
        refreshSetting();
        mCards.show(mWindow.getContentPane(), "main");
        meCurrScreen = eScreenInfo.MAIN;
    }

    public void showDifficulty() {
        mbMulti = false;
        refreshSetting();
        mCards.show(mWindow.getContentPane(), "difficulty");
        meCurrScreen = eScreenInfo.DIFFICULTY;
    }

    public void showGame() {
        mbMulti = false;
        refreshSetting();
        mCards.show(mWindow.getContentPane(), "game");
        meCurrScreen = eScreenInfo.GAME;
    }

    public void showItemGame() {
        mbMulti = false;
        refreshSetting();
        mCards.show(mWindow.getContentPane(), "itemgame");
        meCurrScreen = eScreenInfo.ITEMGAME;
    }

    public void showMultiGame() {
        mbMulti = true;
        refreshSetting();
        mCards.show(mWindow.getContentPane(), "multigame");
        meCurrScreen = eScreenInfo.MULTIGAME;
    }

    public void showItemMultiGame() {
        mbMulti = true;
        refreshSetting();
        mCards.show(mWindow.getContentPane(), "itemmultigame");
        meCurrScreen = eScreenInfo.ITEMMULT;
    }

    public void showTimeLimitGame() {
        mbMulti = true;
        refreshSetting();
        mCards.show(mWindow.getContentPane(), "timelimitgame");
        meCurrScreen = eScreenInfo.TIMELIMIT;
    }

    public void showMultiModeSelect() {
        mbMulti = false;
        refreshSetting();
        mCards.show(mWindow.getContentPane(), "multimode");
        meCurrScreen = eScreenInfo.MULTI_MODE_SELECT;
    }

    public void showSetting() {
        mbMulti = false;
        refreshSetting();
        mCards.show(mWindow.getContentPane(), "setting");
        meCurrScreen = eScreenInfo.SETTING;
    }

    public void showScreenSize() {
        mbMulti = false;
        refreshSetting();
        mCards.show(mWindow.getContentPane(), "screensize");
        meCurrScreen = eScreenInfo.SET_SCREEN_SIZE;
    }

    public void showColorBlind() {
        mbMulti = false;
        refreshSetting();
        mCards.show(mWindow.getContentPane(), "colorblind");
        meCurrScreen = eScreenInfo.SET_COLOR_BLIND_MODE;
    }

    public void showKeySetting() {
        mbMulti = false;
        refreshSetting();
        mCards.show(mWindow.getContentPane(), "keysetting");
        meCurrScreen = eScreenInfo.SET_GAME_KEY;
    }

    public void showMultiKeySetting() {
        mbMulti = false;
        refreshSetting();
        mCards.show(mWindow.getContentPane(), "multikeysetting");
        meCurrScreen = eScreenInfo.SET_MULTI_GAME_KEY;
    }

    public void showScore() {
        mbMulti = false;
        refreshSetting();
        mCards.show(mWindow.getContentPane(), "score");
        meCurrScreen = eScreenInfo.SCOREBOARD;
    }
}