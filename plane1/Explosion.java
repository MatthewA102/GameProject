package plane1;

import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Explosion {

    private int speed;
    private int frames, x, y;
    private int index = 0;
    private int count = 0;
    private BufferedImage img1;
    private BufferedImage img2;
    private BufferedImage img3;
    private BufferedImage img4;
    private BufferedImage img5;
    private BufferedImage img6;
    private BufferedImage currentImg;
    private AudioClip boom;
    private boolean done;

    public Explosion(int speed, BufferedImage img1, BufferedImage img2,
            BufferedImage img3, BufferedImage img4,BufferedImage img5,
            BufferedImage img6, int x, int y, AudioClip boom) {
        this.speed = speed;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.img4 = img4;
        this.img5 = img5;
        this.img6 = img6;
        this.x = x;
        this.y = y;
        this.boom=boom;
        frames = 6;
    }

    public void nextFrame() {

        if (count == 0) {
            currentImg = img1;
        }
        if (count == 1) {
            currentImg = img2;
        }
        if (count == 2) {
            currentImg = img3;
        }
        if (count == 3) {
            currentImg = img4;
        }
        if (count == 4) {
            currentImg = img5;
        }
        if (count == 5) {
            currentImg = img6;
            done=true;
        }

        count++;

        if (count > frames) {
            count = 0;
        }

    }

    public void runAnimation() {
        index++;
        if (index > speed) {
            index = 0;
            nextFrame();
        }
    }

    public void drawAnimation(Graphics ge,ImageObserver obs) {
        ge.drawImage(currentImg, x, y, obs);
        boom.play();
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public int getSpeed() {
        return speed;
    }
    
    public boolean getDone(){
        return done;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
