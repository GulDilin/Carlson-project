package CarlsonProject;

    public class Window{

    private boolean openFlag;
    private boolean holeFlag;
    private boolean speakFlag;
    private float speakChance;
    private Color color;
    private Effects effect;

    public Window(boolean OF, boolean HF, boolean SF, float SC, Color clr, Carlson carlson, Baby baby){
            this.openFlag = OF;
            this.holeFlag = HF;
            this.speakFlag = SF;
            this.speakChance = SC;

            this.color = clr;
            switch (clr){
                case RED:
                    effect = new Effects(Effect.INFINITYJAM, carlson, baby).chance().turns();
                case BLUE:
                    effect = new Effects(Effect.INFINITYJAM, carlson, baby).chance().turns();
                case GREEN:
                    effect = new Effects(Effect.INFINITYJAM, carlson, baby).chance().turns();
                case YELLOW:
                    effect = new Effects(Effect.INFINITYJAM, carlson, baby).chance().turns();
            }
    }

    public boolean isOpenFlag(){
    return this.openFlag;
}

    public boolean isHoleFlag(){
    return this.holeFlag;
}

    public boolean isSpeakFlag(){
    return this.speakFlag
}

    public Color getColor(){
    return this.color;
}

    public String getColorString(){
        switch (this.color){
            case YELLOW:
                return "Желто"
            case GREEN:
                return "Зелено"
            case BLUE:
                return "Сине"
            case RED:
                return "Красно"
        }
    }
    public Effects getEffect(){
        return this.effect;
    }
}