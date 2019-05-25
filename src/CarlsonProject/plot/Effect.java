package CarlsonProject.plot;

import java.io.Serializable;

public enum Effect implements Serializable {
    INFINITYJAM(-1, 0.5D),
    DECENDUR(5, 0.4D),
    ADDENDUR(2, 0.6D),
    ADDJAM(1, 1D);

    private int turns;
    private double chance;

    Effect(int turns, double chance) {
        this.turns = turns;
        this.chance = chance;
    }

     public boolean nextTurn(){
        boolean flag = false;
        if (this.turns > 0){
            this.turns--;
            return true;
        } else if (this.turns < 0) {
            flag = true;
        }
        return flag;
    }

    public boolean success(){
        return (this.chance > Math.random());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
