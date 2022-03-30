package graphics.screens;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import graphics.eScreenInfo;

public abstract class Screen extends JPanel {
    public abstract eScreenInfo getUserInput(KeyEvent e);
}
