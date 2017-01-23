
package plane1;

import java.awt.event.KeyEvent;
import java.util.Observable;

/**
 *
 * @author Eri
 */
public class GameEvents extends Observable {

    int type;
    Object event;

    public void setValue(KeyEvent e) {
        //Type 1: when key is pressed
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            type = 1;
        } //Type 2: when key is released
        else if (e.getID() == KeyEvent.KEY_RELEASED) {
            type = 2;
        }
        event = e;
        setChanged();
        // trigger notification
        notifyObservers(this);
    }

    public void setValue(String msg) {
        //for collision
        type = 3;
        event = msg;
        setChanged();
        // trigger notification
        notifyObservers(this);
    }
}
