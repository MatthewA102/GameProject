package plane1;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class game1942WithObserver extends JApplet implements Runnable {

    private Thread thread;
    Image sea;
    Image myPlane;
    BufferedImage[] explosion;
    ImageObserver observer;
    private BufferedImage bimg;
    Graphics2D g2;
    int speed = 1, move = 0, score = 0;
    long count = 80;
    Random generator = new Random(1234567);
    Island I1, I2, I3;
    HealthBar H1, H2, H3, H4;
    Players m1, m2;
    Bullet bullets;
    int w = 640, h = 480; // fixed size window game 
    Enemy e1, e2, e3;
    GameEvents gameEvents;
    Explosion blast;
    AudioClip background, boom;
    String scoreS;

    ArrayList<Bullet> currentBullets = new ArrayList<Bullet>();
    ArrayList<Enemy> currentEnemies = new ArrayList<Enemy>();
    ArrayList<Explosion> currentExplosion = new ArrayList<Explosion>();
    ArrayList<Bullet> currentEnemyBullets = new ArrayList<Bullet>();
    private Image gameOverIMG;

    public void init() {

        this.setFocusable(true);
        setBackground(Color.white);
        Image island1, island2, island3, enemyImg1, enemyImg2, enemyImg3;
        Image HB1, HB2;
        Image bullet, bulletLeft, bulletRight;
        explosion = new BufferedImage[6];

        try {
            sea = ImageIO.read(new File("Resources/water.png"));
            island1 = ImageIO.read(new File("Resources/island1.png"));
            island2 = ImageIO.read(new File("Resources/island2.png"));
            island3 = ImageIO.read(new File("Resources/island3.png"));
            myPlane = ImageIO.read(new File("Resources/myplane_1.png"));
            HB1 = ImageIO.read(new File("Resources/health.png"));
            HB2 = ImageIO.read(new File("Resources/health2.png"));
            enemyImg1 = ImageIO.read(new File("Resources/enemy1_1.png"));
            enemyImg2 = ImageIO.read(new File("Resources/enemy2_1.png"));
            enemyImg3 = ImageIO.read(new File("Resources/enemy3_1.png"));
            gameOverIMG = ImageIO.read(new File("Resources/gameOver.png"));
            bullet = ImageIO.read(new File("Resources/bullet.png"));
            bulletLeft = ImageIO.read(new File("Resources/bulletLeft.png"));
            bulletRight= ImageIO.read(new File("Resources/bulletRight.png"));
            explosion[0] = ImageIO.read(new File("Resources/explosion1_1.png"));
            explosion[1] = ImageIO.read(new File("Resources/explosion1_2.png"));
            explosion[2] = ImageIO.read(new File("Resources/explosion1_3.png"));
            explosion[3] = ImageIO.read(new File("Resources/explosion1_4.png"));
            explosion[4] = ImageIO.read(new File("Resources/explosion1_5.png"));
            explosion[5] = ImageIO.read(new File("Resources/explosion1_6.png"));

            observer = this;
            gameEvents = new GameEvents();

            I1 = new Island(island1, 100, 100, speed, generator);
            I2 = new Island(island2, 200, 400, speed, generator);
            I3 = new Island(island3, 300, 200, speed, generator);
            boom = playSound("Resources/snd_explosion2.wav");
            background = playSound("Resources/background.mid");
            background.loop();
            H1 = new HealthBar(HB1, 10, 400, m1, gameEvents);
            H2 = new HealthBar(HB2, 10, 400, m1, gameEvents);
            H3 = new HealthBar(HB1, 500, 400, m2, gameEvents);
            H4 = new HealthBar(HB2, 500, 400, m2, gameEvents);

            m1 = new Players(myPlane, bullet, 450, 360, 5, 1, gameEvents,
                    currentBullets, currentExplosion, explosion);
            m2 = new Players(myPlane, bullet, 150, 360, 5, 2, gameEvents,
                    currentBullets, currentExplosion, explosion);
            e1 = new Enemy(enemyImg1, null, null, speed, 1, generator, m1, m2,
                    gameEvents, currentEnemyBullets);
            e2 = new Enemy(enemyImg2, null, null, speed, 2, generator, m1, m2,
                    gameEvents, currentEnemyBullets);
            e3 = new Enemy(enemyImg3, bulletLeft, bulletRight, speed, 3, 
                    generator, m1, m2, gameEvents, currentEnemyBullets);
            currentEnemies.add(e1);
            currentEnemies.add(e2);
            currentEnemies.add(e3);
            gameEvents.addObserver(m1);
            gameEvents.addObserver(m2);
            KeyControl key = new KeyControl(gameEvents);
            addKeyListener(key);
            blast = new Explosion(5, explosion[0], explosion[1], explosion[2],
                    explosion[3], explosion[4], explosion[5], 3, 4, boom);
        } catch (Exception e) {
        }
    }
	
    //music
    private AudioClip playSound(String filename) {
        URL url = game1942WithObserver.class.getResource(filename);
        AudioClip auidoBackground = newAudioClip(url);
        return auidoBackground;
    }

    public void drawBackGroundWithTileImage() {
        int TileWidth = sea.getWidth(this);
        int TileHeight = sea.getHeight(this);

        int NumberX = (int) (w / TileWidth);
        int NumberY = (int) (h / TileHeight);

        for (int i = -1; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                g2.drawImage(sea, j * TileWidth,
                        i * TileHeight + (move % TileHeight), TileWidth,
                        TileHeight, this);
            }
        }
        move += speed;
    }

    public void drawDemo() {
        drawBackGroundWithTileImage();

        //move players & for smooth movement
	//if both m1 and m2 is alive
        if (m1.getAlive()==false || m2.getAlive()==false){
        m1.changeX();
        m1.changeY();
        m2.changeX();
        m2.changeY();

        I1.update();
        I2.update();
        I3.update();
        e1.update();
        e2.update();
        e3.update();

        I1.draw(g2, this);
        I2.draw(g2, this);
        I3.draw(g2, this);
        m1.draw(g2, this);
        m2.draw(g2, this);

        //draws or redraws enemy after colliding with a player bullet
        for (int i = 0; i < currentBullets.size(); i++) {
	    //for every bullet player shoots
            Bullet get = currentBullets.get(i); 
	    //get collision detection for each enemy
            get.Bullet21enemy(e1);
            get.Bullet21enemy(e2);
            get.Bullet21enemy(e3);
	    //if bullet hits e1
            if (get.showBullet == 1) {
		//bullet hit the enemy
                currentBullets.remove(i);
		//explosion: animation and sound
                currentExplosion.add(new Explosion(5, explosion[0],
                        explosion[1], explosion[2], explosion[3], explosion[4],
                        explosion[5], get.getX(), get.getY(), boom));
                e1.reset();
		score+=10;
            } else if (get.showBullet == 2) {
                currentBullets.remove(i);
                currentExplosion.add(new Explosion(5, explosion[0],
                        explosion[1], explosion[2], explosion[3], explosion[4],
                        explosion[5], get.getX(), get.getY(), boom));
                e2.reset();
		score+=20;
            } else if (get.showBullet == 3) {
                currentBullets.remove(i);
                currentExplosion.add(new Explosion(5, explosion[0],
                        explosion[1], explosion[2], explosion[3], explosion[4],
                        explosion[5], get.getX(), get.getY(), boom));
                e3.reset();
		score+=30;
            } else if (get.getY() < 0) {
                currentBullets.remove(i);
            } else {
                get.update(w, h);
                get.draw(g2, this);
            }
        }
        //draws or redraws player after colliding with an enemy bullet
         for (int i = 0; i < currentEnemyBullets.size(); i++) {
            Bullet get = currentEnemyBullets.get(i);
            get.Bullet21player(m1);
            get.Bullet21player(m2);
            if (get.showBullet == 4) {
                currentEnemyBullets.remove(i);
                currentExplosion.add(new Explosion(5, explosion[0],
                        explosion[1], explosion[2], explosion[3], explosion[4],
                        explosion[5], get.getX(), get.getY(), boom));
            } else if (get.showBullet == 5) {
                currentEnemyBullets.remove(i);
                currentExplosion.add(new Explosion(5, explosion[0],
                        explosion[1], explosion[2], explosion[3], explosion[4],
                        explosion[5], get.getX(), get.getY(), boom));

            }  else if (get.getY() < 0) {
                currentEnemyBullets.remove(i);
            } else {
                get.update(w, h);
                get.draw(g2, this);
            }
        }


        //draws or redraws enemy after colliding with player
        for (int i = 0; i < currentEnemies.size(); i++) {
            if (currentEnemies.get(i).enemy2player() == true) {
                if (currentEnemies.get(i).getType() == 1) {
                currentExplosion.add(new Explosion(5, explosion[0],
                        explosion[1], explosion[2], explosion[3], explosion[4],
                        explosion[5], currentEnemies.get(i).getX(), currentEnemies.get(i).getY(), boom));
                    currentEnemies.get(i).reset();
                    score += 10;
                } else if (currentEnemies.get(i).getType() == 2) {
                    currentExplosion.add(new Explosion(5, explosion[0],
                        explosion[1], explosion[2], explosion[3], explosion[4],
                        explosion[5], currentEnemies.get(i).getX(), currentEnemies.get(i).getY(), boom));
                    currentEnemies.get(i).reset();
                    score += 20;
                } else if (currentEnemies.get(i).getType() == 3) {
                    currentExplosion.add(new Explosion(5, explosion[0],
                        explosion[1], explosion[2], explosion[3], explosion[4],
                        explosion[5], currentEnemies.get(i).getX(), currentEnemies.get(i).getY(), boom));
                    currentEnemies.get(i).reset();
                    score += 30;                
                }
            } else {
                currentEnemies.get(i).draw(g2, observer);
            }
        }
        
        //explosion animation
        for (int i = 0;
                i < currentExplosion.size();
                i++) {
            Explosion get = currentExplosion.get(i);
            if (get.getDone() == true) {
                currentExplosion.remove(i);
            } else {
                get.runAnimation();
                get.drawAnimation(g2, observer);
            }
        }
        //Draw Health Bar
        if (m1.getHP()==2){
            H1.draw(g2, observer);
        }else if (m1.getHP()==1){
            H2.draw(g2, observer);
        }
        if (m1.getHP()==2){
            H3.draw(g2, observer);
        }else if (m1.getHP()==1){
            H4.draw(g2, observer);
        }
        }else{
            
            g2.drawImage(gameOverIMG, 200, 200, observer);
            scoreS ="Players' Score: " + score;
            g2.setFont(new Font("default", Font.BOLD, 20));
            g2.drawString(scoreS, 300, 200);
        }
    }

    public void paint(Graphics g) {
        if (bimg == null) {
            Dimension windowSize = getSize();
            bimg = (BufferedImage) createImage(windowSize.width,
                    windowSize.height);
            g2 = bimg.createGraphics();
        }
        drawDemo();
        g.drawImage(bimg, 0, 0, this);
    }

    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }
    

    public void run() { //game loop=timeline

        Thread me = Thread.currentThread();
        while (thread == me) {

            for (int i = 0; i < currentBullets.size(); i++) {
                if (currentBullets.get(i).getA() == false) {
                    gameEvents.addObserver(currentBullets.get(i));
                    currentBullets.get(i).setA(true);
                }
            }

            repaint();
            try {
                thread.sleep(25);
            } catch (InterruptedException e) {
                break;
            }

        }
    }
	
    public static void main(String argv[]) {
        final game1942WithObserver demo = new game1942WithObserver();
        demo.init();
        JFrame f = new JFrame("Scrolling Shooter");
        f.addWindowListener(new WindowAdapter() {
        });
        f.getContentPane().add("Center", demo);
        f.pack();
        f.setSize(new Dimension(640, 480));
        f.setVisible(true);
        f.setResizable(false);
        demo.start();
    }

}
