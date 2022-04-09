package graphics;

import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import data.score.ScoreBoardData;
import data.setting.SettingInfoDesc;
import graphics.screens.GameScreen;
import graphics.screens.MainMenuScreen;
import graphics.screens.ScoreBoardScreen;
import graphics.screens.SettingMenuScreen;

public class WindowManager {
    private JFrame mWindow;
    private CardLayout mCards;
    private MainMenuScreen mMain;
    private GameScreen mGame;
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
                case GAME:
                    switchScreenTo = mGame.getUserInput(e);
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
                case GAME:
                    showGame();
                    break;
                case SETTING:
                    showSetting();
                    break;
                case SCOREBOARD:
                    showScore();
                    break;
                default:
                    assert (false) : "Undefined eScreenInfo";
                    break;

            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

    }

    public WindowManager() {
        SettingInfoDesc mSettingInfoDesc = SettingInfoDesc.getInstance();
        ScoreBoardData scoreBoardInfo = ScoreBoardData.getInstance();
        mWindow = new JFrame();

        int w = mSettingInfoDesc.mScreen.mWidth;
        int h = mSettingInfoDesc.mScreen.mHeight;
        mWindow.setSize(w, h);
        mWindow.setLocation(200, 200);
        mCards = new CardLayout(0, 0);
        mWindow.getContentPane().setLayout(mCards);

        mWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mMain = new MainMenuScreen();
        mGame = new GameScreen();
        mSetting = new SettingMenuScreen();
        mScore = new ScoreBoardScreen();
        mWindow.getContentPane().add("main", mMain);
        mWindow.getContentPane().add("game", mGame);
        mWindow.getContentPane().add("setting", mSetting);
        mWindow.getContentPane().add("score", mScore);

        meCurrScreen = eScreenInfo.MAIN;
        mWindow.addKeyListener(new MsgManager());
        mWindow.setFocusable(true);

        //
        // Define action when closing window message occurs
        //
        mWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(mWindow,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        this.showMain();
        mWindow.setVisible(true);
    }

    private void showMain() {
        mCards.show(mWindow.getContentPane(), "main");
        meCurrScreen = eScreenInfo.MAIN;
    }

    private void showGame() {
        mCards.show(mWindow.getContentPane(), "game");
        meCurrScreen = eScreenInfo.GAME;
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