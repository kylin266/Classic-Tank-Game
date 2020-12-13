package model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainScreenButton extends Button {
    private String fontPath = "src/Assets/fonts/GAMERIA.ttf";
    private static final String buttonPath = "Assets/mainScreenAssets/mainScreenButton.png";
    private boolean isPress = false;
    private static final int WIDTH = 250;
    private static final int HEIGHT = 64;
    private String style = "-fx-background-color: transparent; -fx-background-image: url(Assets/mainScreenAssets/mainScreenButton.png); " +
            "-fx-background-repeat: no-repeat; -fx-background-position: 30%; -fx-background-size: 250 64";

    public MainScreenButton(String text) {
        setText(text);
        setButtonFont();
        setPrefHeight(HEIGHT);
        setPrefWidth(WIDTH);
        setStyle(style);
        buttonEventListener();

    }

    private void setButtonFont() {
        try {
            FileInputStream inp = new FileInputStream(fontPath);
            setFont(Font.loadFont(inp, 23));
        } catch (FileNotFoundException ex) {

            setFont(Font.font("Verdane", 23));
        }
    }

    public void setButtonStyle() {
        if (isPress) {
            setPrefHeight(HEIGHT + 4);
            setLayoutY(getLayoutY() + 2);
        } else {
            setPrefHeight(HEIGHT);
            setLayoutY(getLayoutY() - 2);
        }
    }

    private void buttonEventListener() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    isPress = true;
                    setButtonStyle();
                }
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    isPress = false;
                    setButtonStyle();
                }
            }
        });
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(new DropShadow(4, Color.BLACK));
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(null);
            }
        });
    }
}
