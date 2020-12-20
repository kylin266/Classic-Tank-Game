package model;


import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Bullet extends ImageView {
    private boolean isDead = false;
    public String type;
    private double x;
    private double y;
    public double angle = 0;

    public Bullet(Image img, double x, double y, String type, double angle) {

        super(img);
        setTranslateX(x);
        this.x = x;
        setTranslateY(y);
        this.y = y;
        this.type = type;
        this.angle = angle;
    }

    public Bullet(Image img, String type, double angle) {
        super(img);
        this.type = type;
        this.angle = angle;
    }

    public void setAngleAndPos() {
        if (angle == 0) {
            setVisible(true);
            setRotate(angle);
            setTranslateX(x + 24);
            setTranslateY(y - 20);
        }
        if (angle == 90) {
            setVisible(true);
            setRotate(angle);
            setTranslateX(x + 64);
            setTranslateY(y + 20);

        }
        if (angle == -90) {
            setVisible(true);
            setRotate(angle);
            setTranslateX(x - 24);
            setTranslateY(y + 20);

        }
        if (angle == 180) {
            setVisible(true);
            setRotate(angle);
            setTranslateX(x + 24);
            setTranslateY(y + 54);
        }
    }

    public void moveLeft() {
        setTranslateX(getTranslateX() - 5);
    }

    public void moveRight() {
        setTranslateX(getTranslateX() + 5);
    }

    public void moveUp() {
        setTranslateY(getTranslateY() - 5);
    }

    public void moveDown() {
        setTranslateY(getTranslateY() + 5);
    }

    public void checkCollision(Obstacle model) {
        if (this.getBoundsInParent().intersects(model.getBoundsInParent())) {
            model.gotHit();
            setDead();
        }
    }

    public void setDead() {
        this.setVisible(false);
        this.isDead = true;
    }

    public boolean isDead() {
        return isDead;
    }
}
