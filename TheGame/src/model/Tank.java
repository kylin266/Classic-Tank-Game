package model;

public enum Tank {
    BLUE("Assets/resources/BlueBody.png", "Assets/resources/BlueBarrel.png", "Assets/resources/life.png", "Assets/resources/TankBlue.png", "Assets/resources/BlueLabel.png"),
    BLACK("Assets/resources/BlackBody.png", "Assets/resources/BlackBarrel.png", "Assets/resources/life.png", "Assets/resources/TankBlack.png", "Assets/resources/BlackLabel.png"),
    RED("Assets/resources/RedBody.png", "Assets/resources/RedBarrel.png", "Assets/resources/life.png", "Assets/resources/TankRed.png", "Assets/resources/RedLabel.png");
    private String bodyPath;
    private String barrelPath;
    private String tankLogo;
    private String tankLife;
    private String tankName;

    Tank(String bodyPath, String barrelPath, String tankLife, String tankLogo, String tankName) {
        this.bodyPath = bodyPath;
        this.barrelPath = barrelPath;
        this.tankLife = tankLife;
        this.tankLogo = tankLogo;
        this.tankName = tankName;
    }

    public String getTankLogo() {
        return tankLogo;
    }

    public String getTankName() {
        return tankName;
    }
    public String getTankColor(){
        return bodyPath;
    }
}
