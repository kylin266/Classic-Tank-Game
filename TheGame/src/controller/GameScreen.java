package controller;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
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
import javafx.util.Duration;
import model.*;

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
    private Obstacle tankImg;
    private ImageView lifeImg;
    private Bullet bullet;
    private Obstacle ironBox;
    private double pos_x = 100;
    private double pos_y = 100;
    private boolean isFiring = false;
    private boolean deadType = true;
    private int life;

    /* *
     *
     * Enemies
     * */
    Obstacle enemy1, enemy2, enemy3, enemy4;
    int enemies_number = 4;
    private int board[][] = new int[HEIGHT + 30][WIDTH + 30];

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
            tankImg = new Obstacle(p.getTankImg(), pos_x, pos_y, "player", 0, 3);
            tankImg.setX(pos_x);
            tankImg.setY(pos_y);


            int x = 0;
            life = tankImg.hardness;
            for (int i = 0; i < life; i++) {
                Heart lifeImg = new Heart(new Image(new FileInputStream("src/assets/resources/life.png")));
                ;
                x += 20;
                lifeImg.setX(x);
                lifeImg.setY(10);
                gamePane.getChildren().add(lifeImg);
            }
            gamePane.getChildren().add(tankImg);
            speed = p.getSpeed();

            createGameObstacle();
            createGameLoop();
            createEnemies();
        }
    }

    private void createBullet() {
        Bullet bullet = new Bullet(p.getBulletImg(), tankImg.getX(), tankImg.getY(), "mybullet", angle, 3);
        gamePane.getChildren().add(bullet);
        bullet.setAngleAndPos();
    }

    private void createEnemyBullet(Obstacle enemy) {
        try {
            Bullet bullet = new Bullet(new Image(new FileInputStream("src/assets/resources/bulletBeigeSilver_outline.png")), enemy.getX(), enemy.getY(), "ebullet", enemy.angle, 5);
            gamePane.getChildren().add(bullet);
            bullet.setAngleAndPos();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /* *
    List object show on scene
     */
    List<Obstacle> ironBoxes = new ArrayList<>();
    List<Obstacle> woodenBoxes = new ArrayList<>();
    List<Obstacle> explosiveBarrels = new ArrayList<>();

    private void createGameObstacle() throws FileNotFoundException {
        Random generate = new Random();
        /* ironBox */
        for (int i = 0; i < 8; i++) {
            int rand_x = generate.nextInt(WIDTH - 55);
            int rand_y = generate.nextInt(HEIGHT - 55);
            Obstacle ironBox = new Obstacle(new Image(new FileInputStream("src/assets/obstacle/IronBox.png")), rand_x, rand_y, "ironBox", angle, 5);
            if (board[rand_y][rand_x] == 1 && board[rand_y + 55][rand_x + 55] == 1 && board[rand_y][rand_x + 55] == 1 && board[rand_y + 55][rand_x] == 1&& rand_x != pos_x && rand_y != pos_y) {
                gamePane.getChildren().add(ironBox);
                board = ironBox.setFillBoard(board);
                ironBoxes.add(ironBox);
            }
        }

        /* woodenBoxes */
        for (int i = 0; i < 10; i++) {
            int rand_x = generate.nextInt(WIDTH - 55);
            int rand_y = generate.nextInt(HEIGHT - 55);
            Obstacle woodenBox = new Obstacle(new Image(new FileInputStream("src/assets/obstacle/WoodenBox.png")), rand_x, rand_y, "woodenBox", angle, 2);
            if (board[rand_y][rand_x] == 1 && board[rand_y + 55][rand_x + 55] == 1 && board[rand_y][rand_x + 55] == 1 && board[rand_y + 55][rand_x] == 1&& rand_x != pos_x && rand_y != pos_y) {
                gamePane.getChildren().add(woodenBox);
                board = woodenBox.setFillBoard(board);
                woodenBoxes.add(woodenBox);
            }
        }

        /* Explosive */

        for (int i = 0; i < 8; i++) {
            int rand_x = generate.nextInt(WIDTH - 41);
            int rand_y = generate.nextInt(HEIGHT - 57);
            Obstacle fireBarrel = new Obstacle(new Image(new FileInputStream("src/assets/obstacle/ExplosiveBarrel.png")), rand_x, rand_y, "explosiveBarrel", angle, 0, gamePane);
            if (board[rand_y][rand_x] == 1 && board[rand_y + 57][rand_x + 41] == 1 && board[rand_y][rand_x + 41] == 1 && board[rand_y + 57][rand_x] == 1 && rand_x != pos_x && rand_y != pos_y) {
                gamePane.getChildren().add(fireBarrel);
                board = fireBarrel.setFillBoard(board);
                explosiveBarrels.add(fireBarrel);
            }
        }


    }


    private void createEnemies() {

        /* Enemies*/
        Random generate = new Random();
        boolean is_spawn = false;
        is_spawn = false;
        while (enemy1 == null) {
            int rand_x = generate.nextInt(WIDTH - 70);
            int rand_y = generate.nextInt(HEIGHT - 76);
            if (board[rand_y][rand_x] == 1 && board[rand_y + 76][rand_x + 70] == 1 && board[rand_y][rand_x + 70] == 1 && board[rand_y + 76][rand_x] == 1 && rand_x != pos_x && rand_y != pos_y) {
                try {

                    enemy1 = new Obstacle(new Image(new FileInputStream("src/assets/obstacle/enemy1.png")), rand_x, rand_y, "enemy", angle, 3);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                gamePane.getChildren().add(enemy1);
            }
        }

        is_spawn = false;
        while (enemy2 == null) {
            int rand_x = generate.nextInt(WIDTH - 70);
            int rand_y = generate.nextInt(HEIGHT - 76);
            if (board[rand_y][rand_x] == 1 && board[rand_y + 76][rand_x + 70] == 1 && board[rand_y][rand_x + 70] == 1 && board[rand_y + 76][rand_x] == 1 && rand_x != pos_x && rand_y != pos_y) {
                try {
                    enemy2 = new Obstacle(new Image(new FileInputStream("src/assets/obstacle/enemy2.png")), rand_x, rand_y, "enemy", angle, 3);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                gamePane.getChildren().add(enemy2);
            }
        }


    }


    /*
     * move down =  180, move left = -90, move up = 0, move right = 90
     * */

    private void movePlayer() {
        if (tankImg == null && life != 1) {
            try {
                ImageView gameOver = new ImageView(new Image(new FileInputStream("src/assets/gameOver.png")));
                gamePane.getChildren().add(gameOver);
                gameOver.setX(WIDTH / 2 - 530);
                gameOver.setY(HEIGHT / 2 - 100);
                gameTimer.stop();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (enemy1==null && enemy2==null) {
            try {
                ImageView gameOver = new ImageView(new Image(new FileInputStream("src/assets/victory.png")));
                gamePane.getChildren().add(gameOver);
                gameOver.setX(WIDTH / 2 - 370);
                gameOver.setY(HEIGHT / 2 - 100);
                gameTimer.stop();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
//        if (tankImg.intersects(enemy1.getBoundsInParent()) || life == 0) {
//            gameTimer.stop();
//            System.out.println("gameOver");
//        }

        if (isFiring) {
            createBullet();
            isFiring = false;
        }
        if (tankImg != null) {
            if (isLeftKeyPressed && !isRightKeyPressed && !isDownKeyPressed && !isUpKeyPressed) {
                tankImg.setRotate(-90);
                angle = tankImg.getRotate();
                int cur_x = (int) tankImg.getX();
                int lower_y = (int) tankImg.getY() + 64;
                int middle_y = (int) tankImg.getY() + 32;
                int upper_y = (int) tankImg.getY();
                if (tankImg.getX() > 0) {
                    if (!(board[upper_y][cur_x - speed] == 0 || board[lower_y][cur_x - speed] == 0 || board[middle_y][cur_x - speed] == 0))
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
                    if (!(board[upper_y][cur_x + speed] == 0 || board[lower_y][cur_x + speed] == 0 || board[middle_y][cur_x - speed] == 0))
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
                    if (!(board[cur_y - speed][lower_x] == 0 || board[cur_y - speed][upper_x] == 0 || board[cur_y - speed][middle_x] == 0))
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
                    if (!(board[cur_y + speed][lower_x] == 0 || board[cur_y + speed][upper_x] == 0 || board[cur_y - speed][middle_x] == 0))
                        tankImg.setY(tankImg.getY() + speed);


                }

            }
        }

    }


    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
//                createMovement();
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

    private int createMovement(Obstacle enemy) {
        int speed = 2;
        if (enemy != null) {
            int cur_x, cur_y, lower_y, lower_x, upper_y, upper_x, middle_y, middle_x;
            Random generate = new Random();
            //* Direction chance
            int move = generate.nextInt(3) + 1;
            float f = generate.nextFloat();
            if (f > 0.7 && f < 0.72) {
                switch (move) {
                    case 1:
                        enemy.isMoveDown = enemy.isMoveRight = enemy.isMoveUp = false;
                        enemy.isMoveLeft = true;
                        break;
                    case 2:
                        enemy.isMoveLeft = enemy.isMoveRight = enemy.isMoveDown = false;
                        enemy.isMoveUp = true;
                        break;
                    case 3:
                        enemy.isMoveLeft = enemy.isMoveDown = enemy.isMoveUp = false;
                        enemy.isMoveRight = true;
                        break;
                    case 4:
                        enemy.isMoveLeft = enemy.isMoveRight = enemy.isMoveUp = false;
                        enemy.isMoveDown = true;
                        break;
                }
            }


            if (enemy.isMoveLeft) {
                enemy.setAngle(-90);

                cur_x = (int) enemy.getX();
                lower_y = (int) enemy.getY() + 76;
                middle_y = (int) enemy.getY() + 38;
                upper_y = (int) enemy.getY();

                if (enemy.getX() > 0 && cur_x - speed > 0) {
                    enemy.cur = 1;
                    if (!(board[upper_y][cur_x - speed] == 0 || board[lower_y][cur_x - speed] == 0 || board[middle_y][cur_x - speed] == 0)) {
                        enemy.isMoveDown = enemy.isMoveRight = enemy.isMoveUp = false;
                        enemy.isMoveLeft = true;
                        return enemy.cur;
                    }
                }
                enemy.isMoveDown = enemy.isMoveRight = enemy.isMoveUp = true;

                enemy.isMoveLeft = false;
            }
            if (enemy.isMoveUp) {
                enemy.setAngle(0);

                cur_y = (int) enemy.getY();
                lower_x = (int) enemy.getX();
                upper_x = (int) enemy.getX() + 70;
                middle_x = (int) enemy.getX() + 35;
                if (enemy.getY() > 0 && cur_y - speed > 0) {
                    enemy.cur = 2;
                    if (!(board[cur_y - speed][lower_x] == 0 || board[cur_y - speed][upper_x] == 0 || board[cur_y - speed][middle_x] == 0)) {
                        enemy.isMoveLeft = enemy.isMoveRight = enemy.isMoveDown = false;
                        enemy.isMoveUp = true;
                        return enemy.cur;
                    }

                }
                enemy.isMoveLeft = enemy.isMoveRight = enemy.isMoveDown = true;

                enemy.isMoveUp = false;
            }
            if (enemy.isMoveRight) {
                enemy.setAngle(90);
                cur_x = (int) enemy.getX() + 70;
                lower_y = (int) enemy.getY() + 76;
                middle_y = (int) enemy.getY() + 38;
                upper_y = (int) enemy.getY();
                if (enemy.getX() < 1240 && cur_x + speed < WIDTH + 30) {
                    enemy.cur = 3;
//                    System.out.println((board[upper_y][cur_x + speed] +" " + board[lower_y][cur_x + speed]+ " "+board[middle_y][cur_x - speed]));
                    if (!(board[upper_y][cur_x + speed] == 0 || board[lower_y][cur_x + speed] == 0 || board[middle_y][cur_x + speed] == 0)) {
                        enemy.isMoveLeft = enemy.isMoveDown = enemy.isMoveUp = false;
                        enemy.isMoveRight = true;
                        return enemy.cur;
                    }


                }
                enemy.isMoveLeft = enemy.isMoveDown = enemy.isMoveUp = true;

                enemy.isMoveRight = false;

            }
            if (enemy.isMoveDown) {
                enemy.setAngle(180);

                cur_y = (int) enemy.getY() + 76;
                lower_x = (int) enemy.getX();
                upper_x = (int) enemy.getX() + 70;
                middle_x = (int) enemy.getX() + 35;

                if (enemy.getY() < 660 && cur_y + speed < HEIGHT + 30) {
                    enemy.cur = 4;
                    if (!(board[cur_y + speed][lower_x] == 0 || board[cur_y + speed][upper_x] == 0 || board[cur_y + speed][middle_x] == 0)) {
                        enemy.isMoveLeft = enemy.isMoveRight = enemy.isMoveUp = false;
                        enemy.isMoveDown = true;
                        return enemy.cur;

                    }

                }

            }

            enemy.isMoveUp = enemy.isMoveDown = enemy.isMoveLeft = enemy.isMoveRight = true;
            enemy.isMoveDown = false;

            return 0;
        }
        return 5;
    }

    private void update() {

        if (sprites() != null) {
            sprites().forEach(s -> {
                switch (s.type) {
                    case "mybullet": {

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
                        //ironbox

                        if (s != null && ironBoxes.size() != 0) {
                            for (int i = 0; i < ironBoxes.size(); i++) {
                                Obstacle x = ironBoxes.get(i);
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
                        //woodbox
                        if (s != null && woodenBoxes.size() != 0) {
                            for (int i = 0; i < woodenBoxes.size(); i++) {
                                Obstacle x = woodenBoxes.get(i);
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
                        //explosion
                        if (s != null && explosiveBarrels.size() != 0) {
                            for (int i = 0; i < explosiveBarrels.size(); i++) {
                                Obstacle x = explosiveBarrels.get(i);
                                s.checkCollision(x);
                                if (x.getIsDead() == true) explosiveBarrels.remove(i);
                            }

                            gamePane.getChildren().removeIf(n -> {
                                if (n.getClass().getName().equals("model.Obstacle")) {
                                    Obstacle a = (Obstacle) n;
                                    if (a.getObstacleType().equals("explosiveBarrel") && a.getIsDead() == true) {
                                        board = a.setClearBoard(board);
                                        return true;
                                    }
                                    return false;
                                }
                                return false;
                            });
                        }


                        if (s != null && enemy1 != null) {
                            s.checkCollision(enemy1);
                            if (enemy1.getIsDead()) {
                                board = enemy1.setClearBoard(board);
                                gamePane.getChildren().remove(enemy1);
                                enemy1 = null;
                            }
                        }
                        if (s != null && enemy2 != null) {
                            s.checkCollision(enemy2);
                            if (enemy2.getIsDead()) {
                                board = enemy2.setClearBoard(board);
                                gamePane.getChildren().remove(enemy2);
                                enemy2 = null;
                            }
                        }
                    }
                    case "ebullet": {
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
                        //ironbox

                        if (s != null && ironBoxes.size() != 0) {
                            for (int i = 0; i < ironBoxes.size(); i++) {
                                Obstacle x = ironBoxes.get(i);
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
                        //woodbox
                        if (s != null && woodenBoxes.size() != 0) {
                            for (int i = 0; i < woodenBoxes.size(); i++) {
                                Obstacle x = woodenBoxes.get(i);
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
                        //explosion
                        if (s != null && explosiveBarrels.size() != 0) {
                            for (int i = 0; i < explosiveBarrels.size(); i++) {
                                Obstacle x = explosiveBarrels.get(i);
                                s.checkCollision(x);
                                if (x.getIsDead() == true) explosiveBarrels.remove(i);
                            }

                            gamePane.getChildren().removeIf(n -> {
                                if (n.getClass().getName().equals("model.Obstacle")) {
                                    Obstacle a = (Obstacle) n;
                                    if (a.getObstacleType().equals("explosiveBarrel") && a.getIsDead() == true) {
                                        board = a.setClearBoard(board);
                                        return true;
                                    }
                                    return false;
                                }
                                return false;
                            });
                        }

                        if (s != null && tankImg != null) {
                            if (tankImg.intersects(s.getBoundsInParent()) && s.type.equals("ebullet")) {
                                tankImg.gotHit();
                                s.setDead();
                            }
                            if (life != tankImg.hardness) {
                                gamePane.getChildren().removeIf(n -> {
                                    if (n.getClass().getName().equals("model.Heart")) {

                                        return true;
                                    }
                                    return false;
                                });
                                life = tankImg.hardness;
                                int x = 0;
                                for (int i = 0; i < life; i++) {
                                    Heart lifeImg = null;
                                    try {
                                        lifeImg = new Heart(new Image(new FileInputStream("src/assets/resources/life.png")));
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }

                                    x += 20;
                                    lifeImg.setX(x);
                                    lifeImg.setY(10);
                                    gamePane.getChildren().add(lifeImg);
                                }
                            }
                            if (tankImg.hardness == 0) {
                                gamePane.getChildren().remove(tankImg);
                                tankImg = null;
                            }
                        }
                    }
                }
            });
        }


        if (enemy1 != null) {
            int nextMove = createMovement(enemy1);
            Random generate = new Random();
            switch (nextMove) {
                case 4:
                    if (enemy1.angle == 180) {
                        enemy1.moveDown();
                    }
                case 2:
                    if (enemy1.angle == 0) {
                        enemy1.moveUp();
                    }
                case 3:
                    if (enemy1.angle == 90) {
                        enemy1.moveRight();
                    }

                case 1:
                    if (enemy1.angle == -90) {
                        enemy1.moveLeft();
                    }
                case 0:
                    float chance = generate.nextFloat();
                    if (chance > 0.6 && chance < 0.63)
                        createEnemyBullet(enemy1);
            }
        }

        if (enemy2 != null) {
            int nextMove = createMovement(enemy2);
            Random generate = new Random();
            switch (nextMove) {
                case 4:
                    if (enemy2.angle == 180) {
                        enemy2.moveDown();
                    }
                case 2:
                    if (enemy2.angle == 0) {
                        enemy2.moveUp();
                    }
                case 3:
                    if (enemy2.angle == 90) {
                        enemy2.moveRight();
                    }

                case 1:
                    if (enemy2.angle == -90) {
                        enemy2.moveLeft();
                    }
                case 0:
                    float chance = generate.nextFloat();
                    if (chance > 0.6 && chance < 0.63)
                        createEnemyBullet(enemy2);
            }
        }
        gamePane.getChildren().removeIf(n -> {
            if (n.getClass().getName().equals("model.Bullet")) {
                Bullet s = (Bullet) n;
                return s.isDead();
            }
            return false;
        });
        gamePane.getChildren().removeIf(n -> {
            if (n.getClass().getName().equals("model.Obstacle")) {
                Obstacle a = (Obstacle) n;
                if (a.getIsDead()) {
                    board = a.setClearBoard(board);
                    return true;
                }
            }
            return false;
        });
    }

    private void createPlayerListener() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {


                if (keyEvent.getCode() == KeyCode.SPACE) {
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

