package model;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Obstacle extends ImageView {
    private double x;
    private double y;
    private String type;
    private double angle;
    private double width;
    private double height;
    private double hardness;
    private boolean isDead = false;
    /*
     * Status
     * */
    public Obstacle(Image img, double x, double y, String type, double angle, int hardness) {
        super(img);
        this.x = x;
        this.y = y;
        setX(x);
        setY(y);
        this.type = type;
        this.angle = angle;
        this.width = img.getWidth();
        this.height = img.getHeight();
        this.hardness = hardness;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void gotHit() {
        if (hardness == 0) {
            this.setVisible(false);
            isDead = true;
        }
        else {
            hardness = hardness - 1;
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), this);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.play();
            fadeTransition.stop();
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
        }
    }
    public boolean getIsDead(){
        return isDead;
    }
    public int[][] setFillBoard (int board[][]){
        int x_pos = (int) this.getX();
        int y_pos = (int) this.getY();
        int corner_x = x_pos + (int) this.getWidth();
        int corner_y = y_pos + (int) this.getHeight();

        for (int i = x_pos; i <= corner_x; i++) {
            for (int j = y_pos; j <= corner_y; j++) {
                board[j][i] = 0;
            }
        }
        return board;
    }
    public int[][] setClearBoard (int board[][]){
        int x_pos = (int) this.getX();
        int y_pos = (int) this.getY();
        int corner_x = x_pos + (int) this.getWidth();
        int corner_y = y_pos + (int) this.getHeight();

        for (int i = x_pos; i <= corner_x; i++) {
            for (int j = y_pos; j <= corner_y; j++) {
                board[j][i] = 1;
            }
        }
        return board;
    }
    public String getObstacleType() {
        return type;
    }
}
