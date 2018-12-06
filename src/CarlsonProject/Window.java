package CarlsonProject;

    public class Window{

    private boolean openFlag;
    private boolean holeFlag;
    private boolean speakFlag;
    private double speakChance;
    private Color color;
    private Effects effect;

    public Window(boolean OF, boolean HF, boolean SF, double SC, Color clr) {
        this.openFlag = OF;
        this.holeFlag = HF;
        this.speakFlag = SF;
        this.speakChance = SC;
        this.color = clr;
    }

    public Window(Color color){
        this((Math.random()>0.5), (Math.random()>0.5), (Math.random()>0.5), Math.random(), color);
    }

    public boolean isOpenFlag() {
            return this.openFlag;
    }

    public boolean isHoleFlag() {
            return this.holeFlag;
    }

    public boolean isSpeakFlag() {
            return this.speakFlag
    }

    public Color getColor() {
            return this.color;
    }

    public Effects getEffect(){
        return this.effect;
    }
}