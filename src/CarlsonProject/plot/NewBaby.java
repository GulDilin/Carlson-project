package CarlsonProject.plot;

public class NewBaby extends Baby implements Describeable{
    private double speakVolume;

    public NewBaby(String name, int jamC, double volume){
        super(name, jamC);
        this.speakVolume = volume;
    }

    public NewBaby(String name, int jamC){
        this(name, jamC, Math.random() * 100);
    }

    public double getSpeakVolume() {
        return this.speakVolume;
    }

    @Override
    public boolean talk(){
        if (Math.random() < 0.4) {
            System.out.println("Малыш " + this.toString() + " хочет поглядеть на жуликов\n");
            return true;
        } else {
           return false;
        }
    }
}
