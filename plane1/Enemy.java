package plane1;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {

    Random generator = new Random(1234567);
    Graphics2D g2;
    Image img;
    Image bullet, bullet2;
    int x, y, sizeX, sizeY, speed, type;
    private int temp =0;
    Random gen;
    boolean show, checking = false;
    private boolean dead=false;
    ArrayList bulletList;
    Players m1, m2;
    GameEvents ge;

    Enemy(Image img, Image bullet, Image bullet2, int speed, int type, Random gen, Players m1,
            Players m2, GameEvents ge, ArrayList<Bullet> bulletList) {
        this.img = img;
        this.x = Math.abs(gen.nextInt() % (600 - 30));
        this.y = -20;
        this.speed = speed;
        this.gen = gen;
        this.show = true;
        this.m1 = m1;
        this.m2 = m2;
        this.ge = ge;
        this.type = type;
        this.bullet = bullet;
        this.bullet2 = bullet2;
        this.bulletList = bulletList;
        sizeX = img.getWidth(null);
        sizeY = img.getHeight(null);
    }

    //getters and setters
    void setC(boolean set) { checking = set; }
    public boolean getAlive(){return show;}
    public int getX(){return x;}
    public int getY(){return y;}
    public int getType(){return type;}

    //enemy update per type
    public void update() {
        y += speed;
        if (type==1){
            
        }
        else if(type==2){
            y += 2*speed; 
            
        }else if (type==3){
            if(temp ==50) {
                    if(y >50){
                    bulletList.add( new Bullet(bullet2, -5, 
                                    x + 17, y+17, 2, ge));
                    bulletList.add( new Bullet(bullet, -5, 
                                    x + 17, y+17, 3, ge));
                    }
                    
                    temp = 0;
                    
                }
                else temp++;
            
        }
        
    }
    
    boolean enemy2player()
    {
        if(this.getBounds().intersects(m1.getBounds()))
        {
            ge.setValue("BulletHitPlayer1");
            return dead=true;
        }
        else if(this.getBounds().intersects(m2.getBounds()))
        {
            ge.setValue("BulletHitPlayer2");
            return dead=true;
        }
        return dead=false;
    }

    public void reset() {
        this.x = Math.abs(generator.nextInt() % (600 - 30));
        this.y = -10;
    }

    public void draw(Graphics2D g2, ImageObserver obs) {
        if (show) {
            g2.drawImage(img, x, y, obs);
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }

}