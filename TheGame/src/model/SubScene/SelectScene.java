package model.SubScene;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import model.Tank;
import model.TankChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectScene extends SubScene {
    private static final int HEIGHT = 450;
    private static final int WIDTH = 670;
    private boolean isShown = false;
    private AnchorPane subRoot;
    //Assets Path
    private static final String PanelPath = "src/Assets/mainScreenAssets/ChooseTank.png";
    private File filePanel = new File(PanelPath);
    List<TankChooser> tankList;
    private Tank ChoosenTank;

    public SelectScene() {
        super(new AnchorPane(), WIDTH, HEIGHT);
        prefHeight(HEIGHT);
        prefWidth(WIDTH);
        subRoot = (AnchorPane) this.getRoot();
        createBackGround();
        HBox box = createTankLayout();
        subRoot.getChildren().add(box);
        setLayoutX(1110);
        setLayoutY(250);
    }

    private void createBackGround() {
        String localURL = filePanel.toURI().toString();
        Image img = new Image(localURL, WIDTH, HEIGHT, false, false);
        BackgroundImage view = new BackgroundImage(img, null, null, BackgroundPosition.DEFAULT, null);
        subRoot.setBackground(new Background(view));
    }

    /*
     * Creating elements hbox
     *
     * */
    private HBox createTankLayout() {
        HBox box = new HBox();
        box.setSpacing(60);
        tankList = new ArrayList<>();
        for (Tank tank : Tank.values()) {
            TankChooser tankToChoose = new TankChooser(tank);
            box.getChildren().add(tankToChoose);
            tankList.add(tankToChoose);
            tankToChoose.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (TankChooser tank : tankList) {
                        tank.setChoosen(false);
                        tank.setEffect(null);
                    }
                    tankToChoose.setEffect(new Glow());
                    tankToChoose.setChoosen(true);
                    ChoosenTank = tankToChoose.getTank();
                }
            });
        }
        box.setLayoutX(300 - (90 * 2));
        box.setLayoutY(150);
        return box;
    }

    //moving scene
    public void moveSubscene() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.3));
        transition.setNode(this);
        if (!isShown) {
            transition.setToX(-700);
            isShown = true;
        } else {
            transition.setToX(+700);
            isShown = false;
        }
        transition.play();
    }

    public boolean isShown() {
        return isShown;
    }
}
