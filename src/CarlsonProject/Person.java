package CarlsonProject;
//abstract class for realise Carlson and Baby
public abstract class Person implements EnduranceChanger{

    private String name;
    private int endurance; //выносливость
    final private int MAXENDUR;

    public Person(String name, int endur){
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

    public abstract void rest();
    public abstract void applyEffect(Window window);

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
        return ((this.name == p.getName()) && (this.endurance == p.getEndurance()));
    }

    @Override
    public int hashCode() {
        System.out.println("Ха Ха метод то переопределён");
        return 555;
    }
}
