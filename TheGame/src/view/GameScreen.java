package view;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
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
import model.Player;
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
    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean isUpKeyPressed;
    private boolean isDownKeyPressed;
    private int angle = 90;
    private int speed;
    private ImageView tankImg;

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
            Player p = new Player(tank);
            tankImg = p.getTankImg();
            gamePane.getChildren().add(tankImg);
            speed = p.getSpeed();
            createGameLoop();
        }
    }

    private void movePlayer() {
        if (isLeftKeyPressed && !isRightKeyPressed) {
            System.out.println(angle);
            if (tankImg.getLayoutX() > 0) {
                tankImg.setLayoutX(tankImg.getLayoutX() - speed);
                isLeftKeyPressed = false;

            }
        }
        if (isRightKeyPressed && !isLeftKeyPressed) {


            System.out.println(angle);

            if (tankImg.getLayoutX() < 1300) {
                tankImg.setLayoutX(tankImg.getLayoutX() + speed);
                isRightKeyPressed = false;

            }
        }
        if (isUpKeyPressed && !isDownKeyPressed) {

            System.out.println(angle);

            if (tankImg.getLayoutY() > 0) {
                tankImg.setLayoutY(tankImg.getLayoutY() - speed);
                isUpKeyPressed = false;

            }

        }
        if (isDownKeyPressed && !isUpKeyPressed) {

            System.out.println(angle);

            if (tankImg.getLayoutY() < 708) {
                tankImg.setLayoutY(tankImg.getLayoutY() + speed);
                isDownKeyPressed = false;

            }
        }
    }

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                movePlayer();
            }
        };
        gameTimer.start();
    }


    private void createPlayerListener() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = true;
                    System.out.println("left" + "current" + angle);
                }
                if (keyEvent.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = true;
                    System.out.println("right" + "current" + angle);
                }
                if (keyEvent.getCode() == KeyCode.UP) {
                    isUpKeyPressed = true;
                    System.out.println("up" +  "current" + angle);
                }
                if (keyEvent.getCode() == KeyCode.DOWN) {
                    isDownKeyPressed = true;
                    System.out.println("down" + "current" + angle);

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
