package graphics.screens.screen;

import java.awt.event.KeyListener;

public interface IScreen {
    public void setKeyListener(KeyListener kl);

    public void unsetKeyListener();
}
