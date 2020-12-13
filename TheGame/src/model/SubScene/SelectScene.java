package model.SubScene;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.util.Duration;

import java.io.File;

public class SelectScene extends SubScene {
    private static final int HEIGHT = 450;
    private static final int WIDTH = 670;
    private boolean isShown = false;
    private AnchorPane subRoot;
    //Assets Path
    private static final String PanelPath = "src/Assets/mainScreenAssets/ChooseTank.png";
    private File filePanel = new File(PanelPath);
    public SelectScene() {
        super(new AnchorPane(), WIDTH, HEIGHT);
        prefHeight(HEIGHT);
        prefWidth(WIDTH);
        subRoot = (AnchorPane) this.getRoot();
        createBackGround();
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
    * Creating elements
    *
    * */
    public void createLayout(){

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
