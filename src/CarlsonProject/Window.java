package CarlsonProject;

    public class Window{

    private boolean openFlag;
    private boolean holeFlag;
    private boolean speakFlag;
    private float speakChance;
    private Color color;
    private Effects effect;

    public Window(boolean OF, boolean HF, boolean SF, float SC, Color clr, Carlson carlson, Baby baby) {
        this.openFlag = OF;
        this.holeFlag = HF;
        this.speakFlag = SF;
        this.speakChance = SC;
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