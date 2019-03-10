package CarlsonProject;

public class Window{
    private boolean openFlag;
    private boolean holeFlag;
    private boolean speakFlag;
    private boolean robberFlag;
    private final double robberChance;
    private final double speakChance;
    private final double openChance;
    private final double holeChance;
    private final Color color;

    public static class Builder{
        private final Color color;

        private double robberChance = 0.55D;
        private double openChance = 0.5D;
        private double holeChance = 0.7D;
        private double speakChance = 0.4D;

        public Builder(Color clr) {
            this.color = clr;
        }

        public Builder holeChance(double val){
            this.holeChance = val;
            return this;
        }

        public Builder openChance(double val){
            this.openChance = val;
            return this;
        }

        public Builder speakChance(double val){
            this.speakChance = val;
            return this;
        }

        public Builder robberChance(double val){
            this.robberChance = val;
            return this;
        }

        public Window build(){
            return new Window(this);
        }
    }

    private Window(Builder builder){
        this.color = builder.color;
        this.speakChance = builder.speakChance;
        this.holeChance = builder.holeChance;
        this.openChance = builder.openChance;
        this.robberChance = builder.robberChance;
        this.robberFlag = Math.random() < robberChance;
        this.openFlag = Math.random() < openChance;
        this.holeFlag =  Math.random() > holeChance;
        this.speakFlag = Math.random() < speakChance;
    }

    public boolean isOpenFlag() {
            return this.openFlag;
    }

    public boolean isHoleFlag() {
            return this.holeFlag;
    }

    public boolean isSpeakFlag() {
            return this.speakFlag;
    }

    public boolean isRobberFlag(){
        return this.robberFlag;
    }

    public Color getColor() {
            return this.color;
    }

    public boolean equals(Window w) {
        return (this.openFlag == w.openFlag) &
                (this.holeFlag == w.holeFlag) &
                (this.speakFlag == w.speakFlag) &
                (this.color == w.color);
    }

    public void setFlags(){
        this.speakFlag = Math.random() < this.speakChance;
        this.openFlag = Math.random() < this.openChance;
        this.holeFlag = Math.random() < this.holeChance;
    }
    @Override
    public int hashCode(){
        int count = 0;
        for (char c: this.color.toString().toCharArray()){
            count += (int)c;
        }
        boolean flags[] = {this.holeFlag, this.openFlag, this.speakFlag};
        for(boolean flag: flags){
            count += flag ? 1 : 0;
        }
        return count;
    }

    @Override
    public String toString(){
        return this.getColor().toString() + "е окно";
    }
}