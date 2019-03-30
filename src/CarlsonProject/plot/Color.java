package CarlsonProject.plot;

public enum Color{
    GREEN(Effect.INFINITYJAM, "Зелено"),
    BLUE(Effect.DECENDUR, "Сине"),
    RED(Effect.ADDJAM, "Красно"),
    YELLOW(Effect.ADDENDUR, "Желто");

    private Effect effect;
    private String name;

    private Color(Effect eff, String name){
        this.effect = eff;
        this.name = name;
    }

    public Effect getEffect() {
        return this.effect;
    }

    public String getColorName(){
        return this.name;
    }

    public boolean equals(Color c){
        return (this.name() == c.name()) & (this.effect == c.getEffect());
    }

}