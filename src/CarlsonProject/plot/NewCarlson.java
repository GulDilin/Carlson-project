package CarlsonProject;

public class NewCarlson extends Carlson implements Describeable{
    private double speakVolume;

    public NewCarlson(String name, double volume){
        super(name);
        this.speakVolume = volume;
    }

    public NewCarlson(String name){
        this(name, Math.random() * 100);
    }

    public double getSpeakVolume(){
        return this.speakVolume;
    }

    @Override
    public boolean talk(){
        if (Math.random() < 0.5) {
            System.out.println(this.toString() + " говорит о жуликах\n");
            return true;
        } else {
           return false;
        }
    }
}
