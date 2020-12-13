package model;

public enum Tank {
    BLUE("Assets/resources/tankBlue_outline.png", "Assets/resources/barrelBlue_outline.png", "Assets/resources/life.png"),
    GREEN("Assets/resources/tankGreen_outline.png", "Assets/resources/barrelGreen_outline.png", "Assets/resources/life.png"),
    BLACK("Assets/resources/tankBlack_outline.png", "Assets/resources/barrelBlack_outline.png", "Assets/resources/life.png"),
    RED("Assets/resources/tankRed_outline.png", "Assets/resources/barrelRed_outline.png", "Assets/resources/life.png");
    private String bodyPath;
    private String barrelPath;
    private String tankLife;
    Tank(String bodyPath, String barrelPath, String tankLife){
            this.bodyPath = bodyPath;
            this.barrelPath = barrelPath;
            this.tankLife = tankLife;
    }


}
