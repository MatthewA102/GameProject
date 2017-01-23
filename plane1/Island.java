package plane1;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Island {

    Graphics2D g2;
    Image img;
    int x, y, speed;
    int w = 640, h = 480; // fixed size window game 
    Random gen;

    Island(Image img, int x, int y, int speed, Random gen) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.gen = gen;
    }

    public void update() {
        y += speed;
        if (y >= h) {
            y = -100;
            x = Math.abs(gen.nextInt() % (w - 30));
        }
    }

    public void draw(Graphics2D g2, ImageObserver obs) {
        g2.drawImage(img, x, y, obs);
    }
}
