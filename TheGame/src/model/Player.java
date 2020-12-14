package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class Player {
    private Tank tank;
    private int life = 3;
    private double pos_x = 100;
    private double pos_y = 100;
    private ImageView tankImg;
    private ImageView barrelImg;
    private int speed = 5;
    public Player(Tank tank){
        this.tank = tank;
        createTankImg();
    }
    public void createTankImg(){
        String bodyPath = tank.getBodyPath();
        String barrelPath = tank.getBarrelPath();
        File body =  new File(bodyPath);
        File barrel =  new File(barrelPath);
        tankImg = new ImageView(new Image(body.getPath(), 64, 64, false, false));
        barrelImg = new ImageView(new Image(barrel.getPath(), 24, 58, false, false));
    }

    public ImageView getTankImg() {
        return tankImg;
    }

    public ImageView getBarrelImg() {
        return barrelImg;
    }

    public int getSpeed() {
        return speed;
    }
}
