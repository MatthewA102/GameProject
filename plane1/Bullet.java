package plane1;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

public class Bullet implements Observer {

    Image img;
    ImageObserver obs;
    int x, y, speed, type;
    boolean added = false;
    boolean blast = false;
    boolean checking = false;
    int showBullet;
    Players player;
    Enemy enemy;
    GameEvents events;

    //Constructor
    Bullet(Image img, int s, int xL, int yL, int type, GameEvents ge) {
        this.img = img;
        speed = s;
        x = xL;
        y = yL;
        this.events=ge;
        this.type = type;
    }

    //getter and setter

    void setCheck(boolean set) {
        checking = set;
    }

    int getShowBullet() {
        return showBullet;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    boolean getE() {
        return blast;
    }

    boolean getA() {
        return added;
    }

    void setA(boolean set) {
        added = set;
    }

    public void update(int w, int h) {
        if (type == 1) {
            y -= speed;
        } else if (type == 2) {
            y -= speed;
            x += speed;
        } else if (type == 3) {
            y -= speed;
            x -= speed;
        }
    }

    //gets bounds for collision
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }

    //draws
    public void draw(Graphics g, ImageObserver obs) {
        g.drawImage(img, x, y, obs);
        this.obs = obs;
    }

    /**
     * Collision: player bullet to enemy
     */

    void Bullet21enemy(Enemy enemy) {
        this.enemy = enemy;

        if (this.getBounds().intersects(enemy.getBounds())) {
            events.setValue("BulletHitEnemy");
	    //respawns enemy
            enemy.reset();
            if (enemy.getType()==1){
                this.showBullet=1;
            }else if (enemy.getType()==2){
                this.showBullet=2;
            }
            else if (enemy.getType()==3){
                this.showBullet=3;
            }
        }

    }
    void Bullet21player(Players player){
        this.player = player;
        if (this.getBounds().intersects(player.getBounds())
                && player.getPlayer()==1) {
            events.setValue("BulletHitPlayer1");  
            this.showBullet=4;
        }else if (this.getBounds().intersects(player.getBounds())
                && player.getPlayer()==2){
            events.setValue("BulletHitPlayer2");  
            this.showBullet=5;
        }
    }

    @Override
    public void update(Observable obj, Object arg) {
        GameEvents ge = (GameEvents) arg;
        if (ge.type == 3 && checking) {
            String msg = (String) ge.event;
            if (msg.equals("BulletHitEnemy")
                    || msg.equals("BulletHitPlayer1")
		    || msg.equals("BulletHitPlayer2")) {
                blast = true;
            }
        }

    }
}