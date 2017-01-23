package plane1;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Players implements Observer {

    //variables
    Graphics2D g2;
    Image img, bullet;
    int x, y, speed, width, height, currentSpeedX,
            currentSpeedY, player, temp, hp=2;
    Rectangle bbox;
    boolean alive=false, shooting = false;
    ArrayList bulletList;
    Enemy enemy;
    GameEvents ge;
    ArrayList<Explosion> currentExplosions;
    BufferedImage[] explosion;
    Explosion blast;
    ImageObserver observer;

    //constructor
    Players(Image img, Image bullet, int x, int y, int speed, int playerPort,
            GameEvents ge,  ArrayList<Bullet> bulletList, 
            ArrayList<Explosion> currentExplosions, BufferedImage[] set) {
        this.bulletList = bulletList;
        this.img = img;
        this.bullet = bullet;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.ge = ge;
        this.currentExplosions = currentExplosions;
        this.explosion = set;
        player = playerPort;
    }

    //getter
    boolean getAlive() {
        return alive;
    }
    int getHP(){
        return hp;
    }
    int getPlayer(){
        return player;
    }

    Players(Image myPlane, int i, int i0, int i1, boolean b) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed * 2;
        width = img.getWidth(null);
        height = img.getHeight(null);
    }

    public void draw(Graphics2D g2, ImageObserver obs) {
        g2.drawImage(img, x, y, obs);
    }

    void changeX() {
        if (x >= 10 && x <= 570) {
            x += currentSpeedX;
        } else if (x >= 570) {
            x = 565;
        } else if (x <= 10) {
            x = 15;
        }
    }

    void changeY() {
        if (y >= 0 && y <= 395) {
            y += currentSpeedY;
        } else if (y > 395) {
            y = 390;
        } else if (y < 0) {
            y = 0;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 65, 65);
    }

    public boolean collision(int x, int y, int w, int h) {
        bbox = new Rectangle(this.x, this.y, this.width, this.height);
        Rectangle otherBBox = new Rectangle(x, y, w, h);
        if (this.bbox.intersects(otherBBox)) {
            return true;
        }
        return false;
    }
    
    void Enemy21Player(ArrayList<Enemy> currentEnemy) {
        
        for(int i = 0; i< currentEnemy.size(); i++)
        {
            this.enemy = currentEnemy.get(i);
            if (this.getBounds().intersects(enemy.getBounds())) {
                ge.setValue("BulletHitPlayer");    
                 blast.runAnimation();
                blast.drawAnimation(g2, observer);
                enemy.reset();
            }
        }

    }

    /**
     * there's 6 cases of updates consisting of ge type and player: ge type == 1
     * is for when the key on keyboard is pressed ge type == 2 is for when the
     * key on keyboard is released ge type == 3 is for collision
     *
     * player == 1 is for player 1 player == 2 is for player 2
     */
    public void update(Observable obj, Object arg) {
        GameEvents ge = (GameEvents) arg;
        if ((ge.type == 1) && (player == 1)) {
            KeyEvent e = (KeyEvent) ge.event;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    currentSpeedX = -speed;
                    break;
                case KeyEvent.VK_RIGHT:
                    currentSpeedX = speed;
                    break;
                case KeyEvent.VK_DOWN:
                    currentSpeedY = speed;
                    break;
                case KeyEvent.VK_UP:
                    currentSpeedY = -speed;
                    break;
                default:
                    if (e.getKeyChar() == '/' || shooting) {
                        shooting = true;
                        if (temp < 10) {
                            temp++;
                        } else {
                            temp = 0;
                            Bullet newBullet = new Bullet(bullet, 10, 
                                    x + 17, y, 1, ge);
                            bulletList.add(newBullet);
                        }
                    }
            }
        } else if ((ge.type == 1) && (player == 2)) {
            KeyEvent e = (KeyEvent) ge.event;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    currentSpeedX = -speed;
                    break;
                case KeyEvent.VK_D:
                    currentSpeedX = speed;
                    break;
                case KeyEvent.VK_S:
                    currentSpeedY = speed;
                    break;
                case KeyEvent.VK_W:
                    currentSpeedY = -speed;
                    break;
                default:
                    if (e.getKeyChar() == ' ') {
                        if (temp < 10) {
                            temp++;
                        } else {
                            temp = 0;
                            Bullet newBullet = new Bullet(bullet, 10, 
                                    x + 17, y, 1, ge);
                            bulletList.add(newBullet);
                        }
                    }
            }
        } else if ((ge.type == 2) && (player == 1)) {
            KeyEvent e = (KeyEvent) ge.event;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    currentSpeedX = 0;
                    break;
                case KeyEvent.VK_RIGHT:
                    currentSpeedX = 0;
                    break;
                case KeyEvent.VK_DOWN:
                    currentSpeedY = 0;
                    break;
                case KeyEvent.VK_UP:
                    currentSpeedY = 0;
                    break;
                default:
                    if (e.getKeyChar() == '/') {
                        temp = 0;
                        Bullet newBullet = new Bullet(bullet, 10, 
                                    x + 17, y, 1, ge);
                        bulletList.add(newBullet);
                    }
            }

        } else if ((ge.type == 2) && (player == 2)) {
            KeyEvent e = (KeyEvent) ge.event;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_D:
                    currentSpeedX = 0;
                    break;
                case KeyEvent.VK_A:
                    currentSpeedX = 0;
                    break;
                case KeyEvent.VK_S:
                    currentSpeedY = 0;
                    break;
                case KeyEvent.VK_W:
                    currentSpeedY = 0;
                    break;
                default:
                    if (e.getKeyChar() == ' ') {
                        temp = 0;
                        Bullet newBullet = new Bullet(bullet, 10, 
                                    x + 17, y, 1, ge);
                        bulletList.add(newBullet);
                    }
            }
        } else if (ge.type == 3 && player == 1) {
            String msg = (String) ge.event;
            if (msg.equals("Player1HitEnemy")
                    || msg.equals("BulletHitPlayer1")) {
                hp--;
                if (hp==0){
                    alive = true;
                }
            }
        } else if (ge.type == 3 && player == 2) {
            String msg = (String) ge.event;
            if (msg.equals("Player2HitEnemy")
                    || msg.equals("BulletHitPlayer2")) {
                hp--;
                if (hp==0){
                    alive = true;
                }
                
            }
        }
    }
}
