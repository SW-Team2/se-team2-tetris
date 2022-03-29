package graphics;

import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import data.setting.SettingInfoDesc;
import graphics.screens.GameScreen;
import graphics.screens.MainMenuScreen;
import graphics.screens.ScoreBoardScreen;
import graphics.screens.SettingMenuScreen;

public class WindowManager {
    private static WindowManager mUniqueInstance = null;

    private JFrame mWindow;
    private CardLayout mCards;

    static final String BAR = new String("Bar");

    private WindowManager() {
        SettingInfoDesc mSettingInfoDesc = SettingInfoDesc.getInstance();
        mWindow = new JFrame();

        int w = mSettingInfoDesc.mScreen.mWidth;
        int h = mSettingInfoDesc.mScreen.mHeight;
        mWindow.setSize(w, h);
        mWindow.setLocation(200, 200);
        mCards = new CardLayout(0, 0);
        mWindow.getContentPane().setLayout(mCards);

        mWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mWindow.getContentPane().add("main", new MainMenuScreen());
        mWindow.getContentPane().add("game", GameScreen.getInstance());
        mWindow.getContentPane().add("setting", new SettingMenuScreen());
        mWindow.getContentPane().add("score", new ScoreBoardScreen());

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

        mWindow.setVisible(true);
    }

    public static WindowManager getInstance() {
        if (mUniqueInstance == null) {
            mUniqueInstance = new WindowManager();
        }
        return mUniqueInstance;
    }

    public void show(String name) {
        // TODO: Catch wrong name exception and assert
        mCards.show(mWindow.getContentPane(), name);
    }
}