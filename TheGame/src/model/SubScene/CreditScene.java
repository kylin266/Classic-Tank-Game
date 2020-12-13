package model.SubScene;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.util.Duration;

import java.io.File;

public class CreditScene extends SubScene {
    private static final int HEIGHT = 450;
    private static final int WIDTH = 670;
    private boolean isShown = false;
    private static final String PanelPath = "src/Assets/mainScreenAssets/Credit.png";
    private File filePanel = new File(PanelPath);

    public CreditScene() {
        super(new AnchorPane(), WIDTH, HEIGHT);
        prefHeight(HEIGHT);
        prefWidth(WIDTH);
        createBackGround();
        setLayoutX(1110);
        setLayoutY(250);
    }

    private void createBackGround() {
        String localURL = filePanel.toURI().toString();
        Image img = new Image(localURL, WIDTH, HEIGHT, false, false);
        BackgroundImage view = new BackgroundImage(img, null, null, BackgroundPosition.DEFAULT, null);
        AnchorPane subRoot = (AnchorPane) this.getRoot();
        subRoot.setBackground(new Background(view));
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
