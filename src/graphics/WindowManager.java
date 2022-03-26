package graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.*;

public class WindowManager {
    static WindowManager mUniqueInstance = null;

    public JFrame mWindow;
    public CardLayout mCards;
    public JPanel mMainMenu;
    public JPanel mGameScreen;
    public JPanel mSettingMenu;
    public JPanel mScoreBoard;

    static final String BAR = new String("Bar");

    private WindowManager() {
        mWindow = new JFrame();
        mWindow.setSize(800, 600);
        mWindow.setLocation(200, 200);
        mCards = new CardLayout(0, 0);
        mWindow.getContentPane().setLayout(mCards);

        MakeMainMenu();
        MakeGameScreen();
        MakeSettingMenu();
        MakeScoreBoard();

        mWindow.getContentPane().add("Main", mMainMenu);
        mWindow.getContentPane().add("Game", mGameScreen);
        mWindow.getContentPane().add("Setting", mSettingMenu);
        mWindow.getContentPane().add("Score", mScoreBoard);

        class Input implements KeyListener {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_Q:
                        mCards.show(mWindow.getContentPane(), "Main");
                        break;
                    case KeyEvent.VK_W:
                        mCards.show(mWindow.getContentPane(), "Game");
                        break;
                    case KeyEvent.VK_E:
                        mCards.show(mWindow.getContentPane(), "Setting");
                        break;
                    case KeyEvent.VK_R:
                        mCards.show(mWindow.getContentPane(), "Score");
                        break;
                    default:
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
        mWindow.addKeyListener(new Input());

        mWindow.setVisible(true);
    }

    public static WindowManager getInstance() {
        if (mUniqueInstance == null) {
            mUniqueInstance = new WindowManager();
        }
        return mUniqueInstance;
    }

    private void MakeMainMenu() {
        mMainMenu = new JPanel();
        mMainMenu.setBackground(Color.black);

        JLabel mainLabel = new JLabel("Main");
        mMainMenu.add(mainLabel);
    }

    private void MakeGameScreen() {
        mGameScreen = new JPanel();
        mGameScreen.setBackground(Color.red);

        JLabel mainLabel = new JLabel("Game");
        mGameScreen.add(mainLabel);
    }

    private void MakeSettingMenu() {
        mSettingMenu = new JPanel();
        mSettingMenu.setBackground(Color.blue);

        JLabel mainLabel = new JLabel("Setting");
        mSettingMenu.add(mainLabel);

    }

    private void MakeScoreBoard() {
        mScoreBoard = new JPanel();
        mScoreBoard.setBackground(Color.yellow);

        JLabel mainLabel = new JLabel("Score");
        mScoreBoard.add(mainLabel);
    }
}