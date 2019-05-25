package CarlsonProject.plot;

<<<<<<< HEAD
<<<<<<< HEAD

import CarlsonProject.commands.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Window implements Serializable, Comparable {
=======
public class Window implements Comparable{
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
=======
public class Window implements Comparable{
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
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

        public Builder holeChance(Double val){
            this.holeChance = val;
            return this;
        }

        public Builder openChance(Double val){
            this.openChance = val;
            return this;
        }

        public Builder speakChance(Double val){
            this.speakChance = val;
            return this;
        }

        public Builder robberChance(Double val){
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

    public void setFlags(){
        this.speakFlag = Math.random() < this.speakChance;
        this.openFlag = Math.random() < this.openChance;
        this.holeFlag = Math.random() < this.holeChance;
    }

    public boolean equals(Window otherWindow) {
        return ((this.openChance == otherWindow.openChance)
                && (this.speakChance == otherWindow.speakChance)
                && (this.holeChance == otherWindow.holeChance)
                && (this.robberChance == otherWindow.robberChance)
                && (this.color == otherWindow.color));
    }

    public double getChanceSum(){
        return this.holeChance +
                this.openChance +
                this.robberChance +
                this.speakChance;
    }

    public int getSize(){
        try {
            ByteArrayOutputStream byteObject = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteObject);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
            byteObject.close();
            return byteObject.toByteArray().length;
        }catch (IOException e){
            return 0;
        }
    }

    @Override
    public int hashCode(){
        int count = 0;
        for (char c: this.color.getColorName().toCharArray()){
            count += (int)c;
        }
        boolean flags[] = {this.holeFlag, this.openFlag, this.speakFlag};
        for(boolean flag: flags){
            count += flag ? 1 : 0;
        }
        return count;
    }

    @Override
    public int compareTo(Object w){
        return Double.compare(this.getChanceSum(), ((Window)w).getChanceSum());
    }

    @Override
    public String toString(){
        return "{\"color\":\""+ this.getColor().toString()  +
                "\", \"speakChance\": \"" + this.speakChance +
                "\", \"holeChance\": \"" + this.holeChance +
                "\", \"robberChance\": \"" + this.robberChance +
                "\", \"openChance\": \"" + this.openChance + "\"}";
    }

    @Override
    public int compareTo(Object w){
        return Double.compare(this.getChanceSum(), ((Window)w).getChanceSum());
    }
}