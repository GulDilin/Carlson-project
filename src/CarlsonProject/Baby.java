package CarlsonProject;

final public class Baby extends Person implements Talkable {

    private int jamCounter;
    final private int RESTINC = 5;
    final private int BABYENDUR = 70;
    final private int RESTMIN = BABYENDUR - RESTINC;

    public Baby(String name){
        super(name, BABYENDUR);
    }
    public void followCarlson(Carlson carlson){

    }

    public int getJamCounter(){
        return this.jamCounter;
    }

    public void setJamCounter(int counter){
        this.jamCounter = counter;
    }

    public void die(){
        System.out.println("малыш падает с крыши за ненадобностью");
    }

    public void decJam(){
        if (this.jamCounter > 0){
            this.jamCounter--;
        }
    }

    public boolean hasJam(){
        return this.jamCounter > 0 ? true : false;
    }

    public void addJam(int num){
        this.jamCounter += num;
    }

    @Override
    public void rest(){
        if (this.getEndurance() <= RESTMIN){ incEndurance(RESTINC, this); }
        else{ this.setEndurance(BABYENDUR); }
    }
}