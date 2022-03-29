package graphics;

import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import data.setting.SettingInfoDesc;
import graphics.screens.GameScreen;
import graphics.screens.MainMenuScreen;
import graphics.screens.ScoreBoardScreen;
import graphics.screens.SettingMenuScreen;

public class WindowManager {
    private static WindowManager mUniqueInstance = null;

    private static JFrame mWindow;
    private static CardLayout mCards;

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

        mWindow.getContentPane().add("main", MainMenuScreen.getInstance());
        mWindow.getContentPane().add("game", GameScreen.getInstance());
        mWindow.getContentPane().add("score", new ScoreBoardScreen());
        mWindow.getContentPane().add("setting", new SettingMenuScreen());

        mWindow.setFocusable(true);

        //
        // Define action when closing window message occurs
        //
        mWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
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