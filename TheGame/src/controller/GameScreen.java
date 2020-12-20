package controller;

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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Obstacle;
import model.Player;
import model.Bullet;
import model.Tank;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
     * Animation and Game Element
     * */
    private Player p;
    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean isUpKeyPressed;
    private boolean isDownKeyPressed;
    private boolean isColliding = false;
    private double angle = 0;
    private int speed;
    private ImageView tankImg;
    private Bullet bullet;
    private Obstacle ironBox, enemy1;
    private double pos_x = 100;
    private double pos_y = 100;
    private boolean isFiring = false;
    private boolean deadType = true;

    private int board[][] = new int[HEIGHT + 30 ][WIDTH +30];

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
        gameStage.setTitle("TANK");
        intializeBoard();
        createBackGround();

    }

    private void intializeBoard() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++)
                board[i][j] = 1;
        }
    }

    private void createBackGround() {
        Image img = null;
        try {
            img = new Image(new FileInputStream("src/Assets/map.png"), 1280, 704, false, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BackgroundImage view = new BackgroundImage(img, null, null, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(view));
    }

    private void createPlayer() throws FileNotFoundException {
        if (tank != null) {
            p = new Player(tank);
            tankImg = p.getTankImg();
            tankImg.setX(pos_x);
            tankImg.setY(pos_y);
            gamePane.getChildren().add(tankImg);
            speed = p.getSpeed();

            createGameObstacle();
            createGameLoop();
        }
    }

    private void createBullet() {
        bullet = new Bullet(p.getBulletImg(), tankImg.getX(), tankImg.getY(), "bullet", angle);
        gamePane.getChildren().add(bullet);
        bullet.setAngleAndPos();
    }

    /* *
    List object show on scene
     */
    List<Obstacle> ironBoxes = new ArrayList<>();
    List<Obstacle> woodenBoxes = new ArrayList<>();
    List<Obstacle> enemies = new ArrayList<>();

    private void createGameObstacle() throws FileNotFoundException {
        Random generate = new Random();
        /* ironBox */
        for (int i = 0; i < 11; i++) {
            int rand_x = generate.nextInt(WIDTH - 55);
            int rand_y = generate.nextInt(HEIGHT - 55);
            Obstacle ironBox = new Obstacle(new Image(new FileInputStream("src/assets/obstacle/IronBox.png")), rand_x, rand_y, "ironBox", angle, 5);
            if (board[rand_y][rand_x] == 1 && board[rand_y + 55][rand_x + 55] == 1 && board[rand_y][rand_x + 55] == 1 && board[rand_y + 55][rand_x] == 1) {
                gamePane.getChildren().add(ironBox);
                board = ironBox.setFillBoard(board);
                ironBoxes.add(ironBox);
            }
        }

        /* woodenBoxes */
        for (int i = 0; i < 11; i++) {
            int rand_x = generate.nextInt(WIDTH - 55);
            int rand_y = generate.nextInt(HEIGHT - 55);
            Obstacle woodenBox = new Obstacle(new Image(new FileInputStream("src/assets/obstacle/WoodenBox.png")), rand_x, rand_y, "woodenBox", angle, 2);
            if (board[rand_y][rand_x] == 1 && board[rand_y + 55][rand_x + 55] == 1 && board[rand_y][rand_x + 55] == 1 && board[rand_y + 55][rand_x] == 1) {
                gamePane.getChildren().add(woodenBox);
                board = woodenBox.setFillBoard(board);
                woodenBoxes.add(woodenBox);
            }
        }
//        enemy1 = new Obstacle(new Image(new FileInputStream("src/assets/obstacle/enemy1.png")), 760, 450, "enemy", angle, 5);
//        gamePane.getChildren().add(enemy1);
    }


    private void movePlayer() {
        if (isFiring) {
            createBullet();
        }
        boolean left, right, up, down;
        left = right = up = down = false;

        if (isLeftKeyPressed && !isRightKeyPressed && !isDownKeyPressed && !isUpKeyPressed) {
            tankImg.setRotate(-90);
            angle = tankImg.getRotate();
            int cur_x = (int) tankImg.getX();
            int lower_y = (int) tankImg.getY() + 64;
            int middle_y = (int) tankImg.getY() + 32;
            int upper_y = (int) tankImg.getY();
            if (tankImg.getX() > 0) {
                if (!left && !(board[upper_y][cur_x - speed] == 0 || board[lower_y][cur_x - speed] == 0 || board[middle_y][cur_x - speed] == 0))
                    tankImg.setX(tankImg.getX() - speed);

            }

        }
        if (isRightKeyPressed && !isLeftKeyPressed && !isUpKeyPressed && !isDownKeyPressed) {
            tankImg.setRotate(90);
            angle = tankImg.getRotate();
            int cur_x = (int) tankImg.getX() + 64;
            int lower_y = (int) tankImg.getY() + 64;
            int middle_y = (int) tankImg.getY() + 32;
            int upper_y = (int) tankImg.getY();
            if (tankImg.getX() < 1240) {
                if (!right && !(board[upper_y][cur_x + speed] == 0 || board[lower_y][cur_x + speed] == 0 || board[middle_y][cur_x - speed] == 0))
                    tankImg.setX(tankImg.getX() + speed);

            }

        }
        if (isUpKeyPressed && !isDownKeyPressed && !isLeftKeyPressed && !isRightKeyPressed) {

            tankImg.setRotate(0);
            angle = tankImg.getRotate();
            int cur_y = (int) tankImg.getY();
            int lower_x = (int) tankImg.getX();
            int upper_x = (int) tankImg.getX() + 64;
            int middle_x = (int) tankImg.getX() + 32;
            if (tankImg.getY() > 0) {
                if (!up && !(board[cur_y - speed][lower_x] == 0 || board[cur_y - speed][upper_x] == 0 || board[cur_y - speed][middle_x] == 0))
                    tankImg.setY(tankImg.getY() - speed);

            }

        }
        if (isDownKeyPressed && !isUpKeyPressed && !isLeftKeyPressed && !isRightKeyPressed) {
            tankImg.setRotate(180);
            angle = tankImg.getRotate();
            int cur_y = (int) tankImg.getY() + 64;
            int lower_x = (int) tankImg.getX();
            int upper_x = (int) tankImg.getX() + 64;
            int middle_x = (int) tankImg.getX() + 32;

            if (tankImg.getY() < 660) {
                if (!down && !(board[cur_y + speed][lower_x] == 0 || board[cur_y + speed][upper_x] == 0 || board[cur_y - speed][middle_x] == 0))
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

    private List<Bullet> sprites() {
        List<Bullet> t = new ArrayList<>();
        gamePane.getChildren().stream().forEach(n -> {
            if (n != null) {
                String name = n.getClass().getName();
                if (name.equals("model.Bullet")) t.add((Bullet) n);
            }

        });
        return t;
    }

    private void update() {

        if (sprites() != null) {
            sprites().forEach(s -> {
                switch (s.type) {
                    case "bullet":
                        if (s.angle == 180) {
                            s.moveDown();
                        }
                        if (s.angle == 0) {
                            s.moveUp();
                        }
                        if (s.angle == 90) {
                            s.moveRight();
                        }
                        if (s.angle == -90) {
                            s.moveLeft();
                        }

                        if (s != null && ironBoxes.size() != 0) {
                            for (int i = 0 ; i < ironBoxes.size();i++) {
                                Obstacle x  = ironBoxes.get(i);
                                s.checkCollision(x);
                                if (x.getIsDead() == true) ironBoxes.remove(i);
                            }

                            gamePane.getChildren().removeIf(n -> {
                                if (n.getClass().getName().equals("model.Obstacle")) {
                                    Obstacle a = (Obstacle) n;
                                    if (a.getObstacleType().equals("ironBox") && a.getIsDead() == true) {
                                        board = a.setClearBoard(board);
                                        return true;
                                    }
                                    return false;
                                }
                                return false;
                            });
                        }

                        if (s != null && woodenBoxes.size() != 0) {
                            for (int i = 0 ; i < woodenBoxes.size();i++) {
                                Obstacle x  = woodenBoxes.get(i);
                                s.checkCollision(x);
                                if (x.getIsDead() == true) woodenBoxes.remove(i);
                            }

                            gamePane.getChildren().removeIf(n -> {
                                if (n.getClass().getName().equals("model.Obstacle")) {
                                    Obstacle a = (Obstacle) n;
                                    if (a.getObstacleType().equals("woodenBox") && a.getIsDead() == true) {
                                        board = a.setClearBoard(board);
                                        return true;
                                    }
                                    return false;
                                }
                                return false;
                            });
                        }

                }
            });
        }
        gamePane.getChildren().removeIf(n -> {
            if (n.getClass().getName().equals("model.Bullet")) {
                Bullet s = (Bullet) n;
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


                if (keyEvent.getCode() == KeyCode.SPACE && isFiring == false) {
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


    public void createNewGame(Stage menuStage, Tank choosenTank) throws FileNotFoundException {
        this.menuStage = menuStage;
        this.menuStage.hide();
        this.tank = choosenTank;
        gameStage.show();
        createPlayer();
    }
}
