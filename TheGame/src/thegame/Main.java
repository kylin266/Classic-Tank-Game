package thegame;

import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.GameScreen;
import view.MainScreen;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        MainScreen mainS = new MainScreen();
        GameScreen gameS = new GameScreen();
        primaryStage = mainS.getMainStage();
        primaryStage.setResizable(false);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
