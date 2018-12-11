package CarlsonProject;
//abstract class for realise Carlson and Baby
public abstract class Person implements EnduranceChanger{

    private String name;
    private int endurance; //выносливость
    final private int MAXENDUR;

    public Person(String name, int endur, int minusEndur){
        this.name = name;
        this.endurance = endur;
        this.MAXENDUR = endur;
    }

    public String getName(){
        return name;
    }

    public int getEndurance(){
        return endurance;
    }

    public void setEndurance(int endur){
        this.endurance = endur;
    }

    public int getMAXENDUR() {
        return MAXENDUR;
    }

    public abstract void rest();
    public abstract void applyEffect(Window window);
    public abstract void move();

    @Override
    public void decEndurance(int points, Person person){
        if (person.endurance - points >= 0){
            person.endurance -= points;
        }else { person.endurance = 0;}
    }

    @Override
    public void addEndurance(int points, Person person){
        if (person.endurance + points <= MAXENDUR){
            person.endurance += points;
        }else { person.endurance = MAXENDUR;}
    }

    public boolean equals(Person p) {
        return ((this.name == p.getName()) && (this.MAXENDUR == p.getMAXENDUR()));
    }

    @Override
    public int hashCode() {
        int x = this.MAXENDUR;
        for(char c: this.name.toCharArray()){
            x += (int) c;
        }
        return x % 7757;
    }
}
