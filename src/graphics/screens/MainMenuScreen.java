package graphics.screens;

import graphics.WindowManager;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

public class MainMenuScreen extends JPanel {
    private JPanel buttonGroup;
    private JButton[] menuButton;

    public MainMenuScreen() {
        buttonGroup = new JPanel();
        JLabel title = new JLabel("Main");

        menuButton = new JButton[4];
        String[] buttonText = new String[]{"Start", "Show scoreboard", "Setting", "Exit"};

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.gray);
        this.add(title);
        this.setFocusable(true);

        for (int i = 0; i < menuButton.length; i++) {
            final int buttonIndex = i;
            menuButton[buttonIndex] = new JButton(buttonText[buttonIndex]);

            switch (buttonIndex) {
                case 0:
                    menuButton[buttonIndex].addActionListener(e -> WindowManager.show("game"));
                    break;
                case 1:
                    menuButton[buttonIndex].addActionListener(e -> WindowManager.show("score"));
                    break;
                case 2:
                    menuButton[buttonIndex].addActionListener(e -> WindowManager.show("setting"));
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
                            if (buttonIndex > 0) menuButton[buttonIndex - 1].requestFocus();
                            break;
                        case KeyEvent.VK_DOWN:
                            if (buttonIndex < menuButton.length - 1) menuButton[buttonIndex + 1].requestFocus();
                            break;
                    }
                }
            });
            buttonGroup.add(menuButton[buttonIndex]);
        }
        this.add(buttonGroup);
    }


//    private int getJButtonIndex(final Component component) {
//        for (int i = 0; i < ButtonGroup.getComponentCount(); i++) {
//            if (ButtonGroup.getComponent(i).equals(component)) return i;
//        }
//        return -1;
//    }
}
