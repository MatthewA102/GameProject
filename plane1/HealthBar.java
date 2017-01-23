package plane1;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

public class HealthBar {

    Graphics2D g2;
    Image img;
    int x, y, speed;
    Players player;
    GameEvents ge;

    HealthBar(Image img, int x, int y, Players player, GameEvents ge ) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.player = player;
        this.ge = ge;
    }

    public void draw(Graphics2D g2, ImageObserver obs) {
        g2.drawImage(img, x, y, obs);
    }


}
