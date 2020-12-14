package model;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InfoLabel extends Label {
    private final static String fontPath = "src/Assets/fonts/RussoOne-Regular.ttf";
    private static final int HEIGHT = 49;
    private static final int WIDTH = 190;
    private boolean isPress = false;
    private Color color ;
    public InfoLabel(String text, Color color){
        this.color = color;
        setText(text);
        setPrefHeight(HEIGHT);
//        setPrefWidth(WIDTH);
        setTextAlignment(TextAlignment.CENTER);
        setPadding(new Insets(0,0,0,0));
        setWrapText(true);
        setLabelFont();
        setTextFill(color);
        labelEventListener();
    }
    private void setLabelFont() {
        try {
            FileInputStream inp = new FileInputStream(fontPath);
            setFont(Font.loadFont(inp, 23));
        } catch (FileNotFoundException ex) {

            setFont(Font.font("Verdane", 23));
        }
    }

    public void setLabelStyle() {
        if (isPress) {
            setPrefHeight(HEIGHT + 4);
            setLayoutY(getLayoutY() + 2);
        } else {
            setPrefHeight(HEIGHT);
            setLayoutY(getLayoutY() - 2);
        }
    }


    private void labelEventListener() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    isPress = true;
                }
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    isPress = false;
                }
            }
        });
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyle("-fx-cursor:hand;");

                setTextFill( Color.WHEAT);
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setTextFill(color);
                setStyle("-fx-cursor:pointer;");
            }
        });
    }
}
