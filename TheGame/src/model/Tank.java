package model;

public enum Tank {
    BLUE("assets/resources/BlueBody.png", "assets/resources/BlueBarrel.png", "BLUE", "assets/resources/TankBlue.png", "assets/resources/BlueLabel.png", "assets/resources/bulletBlueSilver_outline.png"),
    BLACK("assets/resources/BlackBody.png", "assets/resources/BlackBarrel.png", "BLACK", "assets/resources/TankBlack.png", "assets/resources/BlackLabel.png", "assets/resources/bulletBeigeSilver_outline.png"),
    RED("assets/resources/RedBody.png", "assets/resources/RedBarrel.png", "RED", "assets/resources/TankRed.png", "assets/resources/RedLabel.png", "assets/resources/bulletRedSilver_outline.png");
    private String bodyPath;
    private String barrelPath;
    private String tankLogo;
    private String tankColor;
    private String tankName;
    private String bullet;
    Tank(String bodyPath, String barrelPath, String tankColor, String tankLogo, String tankName, String bullet) {
        this.bodyPath = bodyPath;
        this.barrelPath = barrelPath;
        this.tankColor = tankColor;
        this.tankLogo = tankLogo;
        this.tankName = tankName;
        this.bullet = bullet;
    }

    public String getTankLogo() {
        return tankLogo;
    }

    public String getTankName() {
        return tankName;
    }

    public String getTankColor() {
        return tankColor;
    }

    public String getBodyPath() {
        return bodyPath;
    }

    public String getBarrelPath() {
        return barrelPath;
    }

    public String getBullet() {
        return bullet;
    }
}
