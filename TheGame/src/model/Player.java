package model;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Player {
    private Tank tank;
    private int life = 3;
    private double pos_x = 100;
    private double pos_y = 100;
    private Image tankImg;
    private ImageView barrelImg;
    private ImageView lifeImg;
    private Image bulletImg;
    private int speed = 4;

    public Player(Tank tank) {
        this.tank = tank;
        createTankImg();
    }

    public void createTankImg() {
        String bodyPath = tank.getTankLogo();
        String barrelPath = tank.getBarrelPath();
        String bulletPath = tank.getBullet();
        File body = new File(bodyPath);
        File barrel = new File(barrelPath);
        File bullet = new File(bulletPath);
        tankImg = new Image(body.getPath(), 64, 64, false, false);
        barrelImg = new ImageView(new Image(barrel.getPath(), 24, 58, false, false));
        bulletImg = new Image(bullet.getPath(), 15, 24, false, false);
    }

    public Image getTankImg() {
        return tankImg;
    }

    public ImageView getBarrelImg() {
        return barrelImg;
    }

    public int getLife(){
        return life;
    }
    public static boolean checkCollision(Obstacle model, ImageView tankImg, double angle) {
        if (!tankImg.getBoundsInParent().intersects(model.getBoundsInParent())) {
            return false;
        }
        return true;
    }

    public int getSpeed() {
        return speed;
    }

    public Image getBulletImg() {
        return bulletImg;
    }
}
