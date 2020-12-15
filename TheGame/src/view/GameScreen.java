package view;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Player;
import model.Sprite;
import model.Tank;

import java.io.File;


public class GameScreen {
    //BACKGROUND
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 704;
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    private Tank tank;
    private String BgPath = "src/Assets/map.png";
    private File fileBG = new File(BgPath);
    private AnimationTimer gameTimer;
    /*
     * Animation
     * */
    private Player p;
    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean isUpKeyPressed;
    private boolean isDownKeyPressed;
    private double angle = 0;
    private int speed;
    private ImageView tankImg;
    private Sprite bullet;
    private double pos_x = 100;
    private double pos_y = 100;
    private boolean isFiring = false;
    private boolean deadType = true;
    public GameScreen() {
        intializeGameStage();
        createPlayerListener();
    }

    private void intializeGameStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, WIDTH, HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        gameStage.setResizable(false);
        createBackGround();
    }

    private void createBackGround() {
        String localURL = fileBG.toURI().toString();
        Image img = new Image(localURL, 1280, 704, false, false);
        BackgroundImage view = new BackgroundImage(img, null, null, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(view));
    }

    private void createPlayer() {
        if (tank != null) {
             p = new Player(tank);
            tankImg = p.getTankImg();
            tankImg.setX(pos_x);
            tankImg.setY(pos_y);
            gamePane.getChildren().add(tankImg);
            speed = p.getSpeed();

            createGameLoop();
        }
    }
    private void createBullet(){
            bullet = new Sprite(p.getBulletImg(), tankImg.getX(),tankImg.getY(),"bullet",angle);
            gamePane.getChildren().add(bullet);
            bullet.move();
            bullet.setDead();
            deadType = bullet.isDead();
    }
    private void movePlayer() {
        if (isFiring) {
//            fire();
            createBullet();
        }
        if (isLeftKeyPressed && !isRightKeyPressed && !isDownKeyPressed && !isUpKeyPressed) {
            tankImg.setRotate(-90);
            angle = tankImg.getRotate();
            if (tankImg.getX() > 0) {

                tankImg.setX(tankImg.getX() - speed);

            }

        }
        if (isRightKeyPressed && !isLeftKeyPressed && !isUpKeyPressed && !isDownKeyPressed) {
            tankImg.setRotate(90);
            angle = tankImg.getRotate();

            if (angle == 180) {
                tankImg.setRotate(-90);
                angle = tankImg.getRotate();
            }

            if (tankImg.getX() < 1240) {


                tankImg.setX(tankImg.getX() + speed);

            }

        }
        if (isUpKeyPressed && !isDownKeyPressed && !isLeftKeyPressed && !isRightKeyPressed) {
            tankImg.setRotate(0);
            angle = tankImg.getRotate();
            if (tankImg.getY() > 0) {
                tankImg.setY(tankImg.getY() - speed);

            }

        }
        if (isDownKeyPressed && !isUpKeyPressed && !isLeftKeyPressed && !isRightKeyPressed) {
            tankImg.setRotate(180);
            angle = tankImg.getRotate();
            if (tankImg.getY() < 660) {
                tankImg.setY(tankImg.getY() + speed);


            }

        }
    }

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                movePlayer();
                update();
            }
        };
        gameTimer.start();
    }
    private void update(){
        gamePane.getChildren().removeIf(n->{
            if (n.getClass().getName()=="Sprite") {
                Sprite s = (Sprite) n;
                return s.isDead();
            }
            return false;
        });
        isFiring = false;
    }

    private void createPlayerListener() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.SPACE && isFiring == false && !deadType ) {
                    isFiring = false;
                }
                if (keyEvent.getCode() == KeyCode.SPACE && isFiring == false && deadType ) {
                    isFiring = true;
                }
                if (keyEvent.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = true;
                }
                if (keyEvent.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = true;
                }
                if (keyEvent.getCode() == KeyCode.UP) {
                    isUpKeyPressed = true;
                }
                if (keyEvent.getCode() == KeyCode.DOWN) {
                    isDownKeyPressed = true;
                }


            }
        });
        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.SPACE) {
                    isFiring = false;
                }
                if (keyEvent.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = false;
                }
                if (keyEvent.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = false;
                }
                if (keyEvent.getCode() == KeyCode.UP) {
                    isUpKeyPressed = false;
                }

                if (keyEvent.getCode() == KeyCode.DOWN) {
                    isDownKeyPressed = false;
                }


            }
        });
    }

    private void createGameObstacle() {

    }


    public void createNewGame(Stage menuStage, Tank choosenTank) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        this.tank = choosenTank;
        gameStage.show();
        createPlayer();
    }
}
