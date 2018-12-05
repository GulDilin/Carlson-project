package CarlsonProject;

public class Effects{

    private int turns = 0;
    private float chance = 1;
    private Effect effectName;

    Effects(Effect eff, Carlson carlson, Baby baby){
        this.effectName = eff;

        final int BABYINC = 5;
        final int CARLINC = 10;

        switch (eff){
            case ADDJAM:
                baby.addJam(1);
                    break;
            case ADDENDUR:
                carlson.setEndurance(carlson.getEndurance() + CARLINC);
                baby.setEndurance(baby.getEndurance() + BABYINC);
                    break;
            case DECENDUR:
                carlson.setEndurance(carlson.getEndurance() - CARLINC);
                baby.setEndurance(baby.getEndurance() - BABYINC);
                    break;
            case INFINITYJAM:
                if (carlson.getJamWasEaten() == 0){
                    baby.addJam(1);
                }
        }
    }

    final Effects turns(int num){
        this.turns = num;
        return this;
    }

    final Effects chance(int chance){
        this.chance = chance;
        return this;
    }

    public boolean success(){
        return (this.chance - 1) > 0? true : false;
    }
}