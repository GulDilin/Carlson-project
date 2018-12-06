package CarlsonProject;

public enum Effect{
    INFINITYJAM(-1, 0.5D),
    DECENDUR(5, 0.4D),
    ADDENDUR(2, 0.6D),
    ADDJAM(1, 1D);

    private int turns;
    private double chance;

    private Effect(int turns, double chance) {
        this.turns = turns;
        this.chance = chance;
    }

    public boolean success(){
        return (this.chance > Math.random());
    }
}
