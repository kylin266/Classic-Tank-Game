package model;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InfoLabel extends Label {
    private final static String fontPath = "src/Assets/fonts/RussoOne-Regular.ttf";

    public InfoLabel(String text){
        setText(text);
        setPrefHeight(400);
        setPrefWidth(600);
        setPadding(new Insets(40,40,40,40));
        setWrapText(true);
        setLabelFont();
    }
    private void setLabelFont() {
        try {
            FileInputStream inp = new FileInputStream(fontPath);
            setFont(Font.loadFont(inp, 23));
        } catch (FileNotFoundException ex) {

            setFont(Font.font("Verdane", 23));
        }
    }
}
