package graphics;

import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import data.score.ScoreBoardData;
import data.setting.SettingData;
import gamestarter.GameStarter;
import graphics.screens.*;
import tetrisgame.enumerations.eDifficulty;

public class WindowManager {
    private JFrame mWindow;
    private CardLayout mCards;
    private MainMenuScreen mMain;
    private DifficultyScreen mDifficulty;
    private GameScreen mGame;
    private ItemModeGameScreen mItemGame;
    private SettingMenuScreen mSetting;
    private ScoreBoardScreen mScore;
    private eScreenInfo meCurrScreen;

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
                case SETTING:
                    switchScreenTo = mSetting.getUserInput(e);
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
                case SETTING:
                    showSetting();
                    break;
                case SCOREBOARD:
                    showScore();
                    break;
                case SET_SCREEN_SIZE:
                    // TODO:
                    break;
                case SET_GAME_KEY:
                    // TODO:
                    break;
                case SET_COLOR_BLIND_MODE:
                    // TODO:
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
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (meCurrScreen) {
                case GAME:
                    mGame.getUserInputKeyRealease(e);
                    break;
                case ITEMGAME:
                    mItemGame.getUserInputKeyRealease(e);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

    }

    public WindowManager() {
        SettingData mSettingInfoDesc = SettingData.getInstance();
        ScoreBoardData scoreBoardInfo = ScoreBoardData.getInstance();
        mWindow = new JFrame();

        int width = mSettingInfoDesc.getWidth();
        int height = mSettingInfoDesc.getHeight();
        mWindow.setSize(width, height);
        mWindow.setLocation(200, 200);
        mCards = new CardLayout(0, 0);
        mWindow.getContentPane().setLayout(mCards);

        mWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mMain = new MainMenuScreen();
        mDifficulty = new DifficultyScreen();
        mGame = new GameScreen();
        mItemGame = new ItemModeGameScreen();
        mSetting = new SettingMenuScreen();
        mScore = new ScoreBoardScreen();
        mWindow.getContentPane().add("main", mMain);
        mWindow.getContentPane().add("difficulty", mDifficulty);
        mWindow.getContentPane().add("game", mGame);
        mWindow.getContentPane().add("itemgame", mItemGame);
        mWindow.getContentPane().add("setting", mSetting);
        mWindow.getContentPane().add("score", mScore);

        meCurrScreen = eScreenInfo.MAIN;
        mWindow.addKeyListener(new MsgManager());
        mWindow.setFocusable(true);

        this.showMain();
        mWindow.setVisible(true);
    }

    public void showMain() {
        mCards.show(mWindow.getContentPane(), "main");
        meCurrScreen = eScreenInfo.MAIN;
    }

    public void showDifficulty() {
        mCards.show(mWindow.getContentPane(), "difficulty");
        meCurrScreen = eScreenInfo.DIFFICULTY;
    }

    private void showGame() {
        mCards.show(mWindow.getContentPane(), "game");
        meCurrScreen = eScreenInfo.GAME;
    }

    private void showItemGame() {
        mCards.show(mWindow.getContentPane(), "itemgame");
        meCurrScreen = eScreenInfo.ITEMGAME;
    }

    private void showSetting() {
        mCards.show(mWindow.getContentPane(), "setting");
        meCurrScreen = eScreenInfo.SETTING;
    }

    private void showScore() {
        mCards.show(mWindow.getContentPane(), "score");
        meCurrScreen = eScreenInfo.SCOREBOARD;
    }
}