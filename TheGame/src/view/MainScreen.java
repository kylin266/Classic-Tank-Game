package view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.stage.Stage;
import model.SubScene.CreditScene;
import model.SubScene.SelectScene;
import model.MainScreenButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainScreen {
    //BACKGROUND
    private static final int WIDTH = 1100;
    private static final int HEIGHT = 750;
    private static final int MENU_BUTTON_START_X = 120;
    private static final int MENU_BUTTON_START_Y = 250;
    private AnchorPane mainPane;
    private Scene mainScene;
    private SelectScene loadingScene;
    private CreditScene creditScene;
    private Stage mainStage;


    /*
     *  Asset Path and Setting
     *
     */
    private static final String BgPath = "src/Assets/mainScreenAssets/mainScreenBG.jpg";
    private static final String BannerPath = "src/Assets/mainScreenAssets/BannerBig.png";
    private File fileBG = new File(BgPath);
    private File fileBanner = new File(BannerPath);

    /*
     * Elements in screen
     *
     * */
    List<MainScreenButton> buttonList = new ArrayList<>();

    public MainScreen() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubScene();
        createBackGround();
        createBanner();
        createMainScreenButtons();
    }

    /*
     * Create main screen buttons
     * */
    private void createMainScreenButtons() {
        createStartButton();
        createAboutButton();
        createExitButton();
    }

    private void createStartButton() {
        MainScreenButton startB = new MainScreenButton("START");
        addMenuButton(startB);

        startB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (creditScene.isShown()) creditScene.moveSubscene();
                loadingScene.moveSubscene();
            }
        });
    }

    private void createAboutButton() {
        MainScreenButton aboutB = new MainScreenButton("ABOUT");
        addMenuButton(aboutB);

        aboutB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (loadingScene.isShown()) loadingScene.moveSubscene();
                creditScene.moveSubscene();
            }
        });
    }

    private void createExitButton() {
        MainScreenButton exitB = new MainScreenButton("EXIT");
        addMenuButton(exitB);
        exitB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });
    }

    private void addMenuButton(MainScreenButton button) {
        try {
            int size = 0;
            if (buttonList != null) size = buttonList.size();
            button.setLayoutX(MENU_BUTTON_START_X);
            button.setLayoutY(MENU_BUTTON_START_Y + size * 130);
            buttonList.add(button);
            mainPane.getChildren().add(button);
        } catch (NullPointerException ex) {

        }
    }

    /*
     * Screen Detail
     * */
    private void createBanner() {
        String localURL = fileBanner.toURI().toString();
        Image img = new Image(localURL);
        ImageView logo = new ImageView(img);
        logo.setLayoutX(280);
        logo.setLayoutY(100);
        mainPane.getChildren().add(logo);
    }


    private void createBackGround() {
        String localURL = fileBG.toURI().toString();
        Image img = new Image(localURL, 1100, 750, false, false);
        BackgroundImage view = new BackgroundImage(img, null, null, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(view));
    }


    /*
    Tank Selecting Scene
     */
    private void createSubScene() {
        loadingScene = new SelectScene();
        mainPane.getChildren().add(loadingScene);
        creditScene = new CreditScene();
        mainPane.getChildren().add(creditScene);
    }

    public Stage getMainStage() {
        return mainStage;
    }
}
