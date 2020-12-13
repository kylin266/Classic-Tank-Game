package model;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class TankChooser extends VBox {
    private ImageView tankImg, tankName;
    private Tank tank;
    private boolean isChoosen = false;

    public TankChooser(Tank tank) {
        tankImg = new ImageView(tank.getTankLogo());
        this.tank = tank;
        tankName = new ImageView(tank.getTankName());
        setAlignment(Pos.CENTER);
        setSpacing(20);
        this.getChildren().add(tankImg);
        this.getChildren().add(tankName);
    }

    public Tank getTank() {
        isChoosen = true;
        return tank;
    }

    public void setChoosen(boolean choosen) {
        isChoosen = choosen;

    }

    public boolean isChoosen() {

        return isChoosen;
    }
}
