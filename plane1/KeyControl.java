package plane1;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyControl extends KeyAdapter {

    GameEvents event;

    KeyControl(GameEvents newEvent) {
        event = newEvent;

    }

    public void keyPressed(KeyEvent e) {
        event.setValue(e);
    }

    public void keyReleased(KeyEvent e) {
        event.setValue(e);
    }
}
