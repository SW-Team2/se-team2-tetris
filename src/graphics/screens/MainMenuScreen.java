package graphics.screens;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import graphics.WindowManager;

public class MainMenuScreen extends JPanel {
    private static MainMenuScreen uniqueInstance = null;
    private final JButton[] menuButton;

    public MainMenuScreen() {
        JPanel buttonGroup = new JPanel();
        JLabel title = new JLabel("Main");

        menuButton = new JButton[4];
        String[] buttonText = new String[] { "Start", "Show scoreboard", "Setting", "Exit" };

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.gray);
        this.add(title);
        this.setFocusable(true);

        for (int i = 0; i < menuButton.length; i++) {
            final int buttonIndex = i;
            menuButton[buttonIndex] = new JButton(buttonText[buttonIndex]);

            switch (buttonIndex) {
                case 0:
                    menuButton[buttonIndex].addActionListener(e -> WindowManager.getInstance().show("game"));
                    break;
                case 1:
                    menuButton[buttonIndex].addActionListener(e -> WindowManager.getInstance().show("score"));
                    break;
                case 2:
                    menuButton[buttonIndex].addActionListener(e -> WindowManager.getInstance().show("setting"));
                    break;
                case 3:
                    menuButton[buttonIndex].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (JOptionPane.showConfirmDialog(MainMenuScreen.this,
                                    "Are you sure you want to close this window?",
                                    "Close Window?", JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                                System.exit(0);
                            }
                        }
                    });
                    System.out.println("exit button clicked");
                    break;
            }
            KeyListener onClickEnter = new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    super.keyPressed(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        ((JButton) e.getComponent()).doClick();
                    }
                }
            };
            menuButton[buttonIndex].addKeyListener(onClickEnter);
            menuButton[buttonIndex].addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    super.keyPressed(e);
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            if (buttonIndex > 0)
                                menuButton[buttonIndex - 1].requestFocus();
                            break;
                        case KeyEvent.VK_DOWN:
                            if (buttonIndex < menuButton.length - 1)
                                menuButton[buttonIndex + 1].requestFocus();
                            break;
                    }
                }
            });
            buttonGroup.add(menuButton[buttonIndex]);
        }
        this.add(buttonGroup);
    }

    public static MainMenuScreen getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MainMenuScreen();
        }
        return uniqueInstance;
    }

    public void setKeyListener(KeyListener kl) {
        super.addKeyListener(kl);
        super.setFocusable(true);
        super.requestFocus();
    }

    public void unsetKeyListener() {
        for (int i = 0; i < menuButton.length; i++)
            menuButton[i].setFocusable(false);
        super.setFocusable(false);
    }
}
