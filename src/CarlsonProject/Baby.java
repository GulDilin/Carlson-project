package CarlsonProject;

final public class Baby extends Person implements Talkable {

    private int jamCounter;
    final private int RESTINC;
    final private int BABYENDUR;
    final private int RESTMIN;
    private Effect effect;
    final private int MINUSENDUR = 5;
    final private int MAXJAM;

    public Baby(String name, int jam){
        super(name,  40);
        this.jamCounter = jam;
        this.MAXJAM = jam;
        this.BABYENDUR = 40;
        this.RESTINC = 5;
        this.RESTMIN = BABYENDUR - RESTINC;
    }

    public int getJamCounter(){
        return this.jamCounter;
    }

    public int getMINUSENDUR() {
        return MINUSENDUR;
    }

    public void setJamCounter(int counter){
        this.jamCounter = counter;
    }

    public void die(){
        System.out.println(this.toString() + " падает с крыши за ненадобностью");
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
    public void applyEffect(Window window){
        effect = window.getColor().getEffect();
        switch (effect){
            case INFINITYJAM:
                this.jamCounter =  this.MAXJAM;
            case DECENDUR:
                this.decEndurance(MINUSENDUR, this);
                break;
            case ADDENDUR:
                final int PLUSENDUR = 4;
                this.addEndurance(PLUSENDUR, this);
                break;
        }
    }

    @Override
    public void say(String what){
        switch (what){
            case "":
                break;
        }
    }

    @Override
    public void printStatus(){
        System.out.printf("У малыша осталось %d банок варенья", this.getJamCounter());
    }

    @Override
    public void rest(){
        if (this.getEndurance() <= RESTMIN){ addEndurance(RESTINC, this); }
        else{ this.setEndurance(BABYENDUR); }
    }

    @Override
    public String toString() {
        return "Малыш " + this.getName();
    }
}