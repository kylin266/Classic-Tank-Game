package model;


import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Sprite extends ImageView {
    private boolean isDead = false;
    private String type;
    private double x;
    private double y;
    private double angle;

    public Sprite(Image img, double x, double y, String type, double angle) {

        super(img);
        setX(x);
        this.x = x;
        setY(y);
        this.y = y;
        this.type = type;
        this.angle = angle;
    }

    public Sprite(Image img, String type, double angle) {
        super(img);
        this.type = type;
        this.angle = angle;
    }

    public void move() {
        if (angle == 0) {
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), this);
            //bullet trigger
            setX(x + 24);
            setY(y - 20);
            setVisible(true);
            setRotate(angle);
            tt.setDuration(Duration.seconds(780 * 0.001));
            tt.setFromY(0);
            tt.setToY(-780-30);
            tt.play();

        }
        if (angle == 90) {
            System.out.println(x);
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), this);
            //bullet trigger
            setX(x + 64);
            setY(y + 20);
            setVisible(true);
            setRotate(angle);
            tt.setDuration(Duration.seconds(-(-1000) * 0.001));
            tt.setFromX(0);
            tt.setToX(1280 + 30);
            tt.play();
        }
         if (angle == -90) {
            System.out.println(x);
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), this);
            //bullet trigger
            setX(x-24);
            setY(y + 20);
            setVisible(true);
            setRotate(angle);
            tt.setDuration(Duration.seconds(-(-1000) * 0.001));
            tt.setFromX(0);
            tt.setToX(-1280 - 30);
            tt.play();
        }
        if (angle == 180) {
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), this);
            //bullet trigger
            setX(x + 24);
            setY(y + 54);
            setVisible(true);
            setRotate(angle);
            tt.setDuration(Duration.seconds(780 * 0.001));
            tt.setFromY(0);
            tt.setToY( 780 + 30);
            tt.play();

        }
    }

    public void setDead() {
        this.isDead = true;
    }

    public boolean isDead() {
        return isDead;
    }
}
