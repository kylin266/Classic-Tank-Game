package model;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Obstacle extends ImageView {
    private double x;
    private double y;
    public String type;
    public double angle;
    private double width;
    private double height;
    public int hardness;
    private boolean isDead = false;
    private Pane gamePane;
    private int[][] board;


    /*
     * Status
     * */
    public boolean canMoveLeft = true, canMoveRight = true, canMoveUp = true, canMoveDown = true;
    public boolean isMoveLeft = true, isMoveRight = true, isMoveUp = true, isMoveDown = true;
    public int cur = 1;
    private int speed = 5;

    public Obstacle() {

    }

    public Obstacle(Image img, double x, double y, String type, double angle, int hardness, Pane gamePane) {
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
        this.gamePane = gamePane;
    }

    public Obstacle(Image img, double x, double y, String type, double angle, int hardness) {
        super(img);
        this.x = x;
        this.y = y;
        this.type = type;
        this.angle = angle;
        this.width = img.getWidth();
        this.height = img.getHeight();
        this.hardness = hardness;
        setX(x);
        setY(y);

    }

    public void setAttribute(String type, Pane gamePane) {
        if (type.equals("explosiveBarrel")) {
            try {
                ImageView boom = new ImageView(new Image(new FileInputStream("src/assets/effects/Boom.png"), 200, 200, false, false));
                boom.setX(this.x - 60);
                boom.setY(this.y - 80);
                gamePane.getChildren().add(boom);
                gamePane.getChildren().forEach(n -> {
                    if (n.getClass().getName().equals("model.Obstacle")) {
                        Obstacle a = (Obstacle) n;
                        if (a.intersects(boom.getBoundsInParent())) {
                            if (a.type.equals("enemy") || a.type.equals("player")) a.gotHit();

                            else a.setDead(true);
                        }

//                        if (a.getIsDead() == true) {
////                            board = a.setClearBoard(board);
//                            return true;
//                        }
                    }
                });
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), boom);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(0.5);
                fadeTransition.play();
                fadeTransition.stop();
                fadeTransition.setFromValue(0.5);
                fadeTransition.setToValue(0.0);
                fadeTransition.play();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
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
            setAttribute(type, gamePane);
            isDead = true;
        } else {
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

    public boolean getIsDead() {
        return isDead;
    }

    public int[][] setFillBoard(int board[][]) {
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


    public int[][] setClearBoard(int board[][]) {
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

    public void setDead(boolean dead) {
        isDead = dead;
    }


    public void moveLeft() {
        setX(getX() - 2);
    }

    public void moveRight() {
        setX(getX() + 2);
    }

    public void moveUp() {
        setY(getY() - 2);
    }

    public void moveDown() {
        setY(getY() + 2);
    }

    public void setAngle(double angle) {
        this.angle = angle;
        setRotate(angle);
    }


}
