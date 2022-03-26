package graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;

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

        mWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MakeMainMenu();
        MakeGameScreen();
        MakeSettingMenu();
        MakeScoreBoard();

        mWindow.getContentPane().add("main", mMainMenu);
        mWindow.getContentPane().add("game", mGameScreen);
        mWindow.getContentPane().add("setting", mSettingMenu);
        mWindow.getContentPane().add("score", mScoreBoard);

        //
        // Define action when keybaord message occurs
        //
        mWindow.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_Q:
                        mCards.show(mWindow.getContentPane(), "main");
                        break;
                    case KeyEvent.VK_W:
                        mCards.show(mWindow.getContentPane(), "game");
                        break;
                    case KeyEvent.VK_E:
                        mCards.show(mWindow.getContentPane(), "setting");
                        break;
                    case KeyEvent.VK_R:
                        mCards.show(mWindow.getContentPane(), "score");
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
        });

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

        mWindow.setVisible(true);
    }

    public static WindowManager getInstance() {
        if (mUniqueInstance == null) {
            mUniqueInstance = new WindowManager();
        }
        return mUniqueInstance;
    }

    public void show(String name) {
        mCards.show(mWindow.getContentPane(), name);
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