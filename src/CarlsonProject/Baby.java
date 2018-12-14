package CarlsonProject;

public class Baby extends Person implements Talkable {

    private int jamCounter;
    final private int RESTINC;
    final private int MAXENDUR;
    final private int RESTMIN;
    private Effect effect;
    final private int MINUSENDUR = 5;
    final private int MAXJAM;

    public Baby(String name, int jam){
        super(name,  30);
        this.jamCounter = jam;
        this.MAXJAM = jam;
        this.MAXENDUR = 10;
        this.RESTINC = 5;
        this.RESTMIN = MAXENDUR - RESTINC;
    }

    public int getJamCounter(){
        return this.jamCounter;
    }

    public int getMINUSENDUR() {
        return MINUSENDUR;
    }

    public void die(){
        say("dead");
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
    public void move(){
        if (effect != null) {
            if (this.effect.nextTurn()) {
                this.applyEffect(Move.getWindows()[Move.getTargetWindowID()]);
            }
        }
        this.decEndurance(MINUSENDUR, this);
    }

    @Override
    public void applyEffect(Window window){
        effect = window.getColor().getEffect();
        switch (effect){
            case ADDJAM:
                if (effect.success() & (this.jamCounter < this.MAXJAM)) {
                    this.jamCounter++;
                }
            case INFINITYJAM:
                if (effect.success()) {
                    this.jamCounter = this.MAXJAM;
                    say("jam");
                }
                break;

            case DECENDUR:
                if (effect.success()) {
                    final int MINUSENDUR = 5;
                    this.decEndurance(MINUSENDUR, this);
                    System.out.println("Выносливость " + this.toString() + " уменьшена на " + MINUSENDUR + " пунктов" );
                }
                break;

            case ADDENDUR:
                if (effect.success()) {
                    final int PLUSENDUR = 2;
                    this.addEndurance(PLUSENDUR, this);
                    System.out.println("Выносливость " + this.toString() + " увеличена на " + PLUSENDUR + " пунктов" );
                }
                break;
        }
    }

    @Override
    public void say(String what){
        switch (what){
            case "dead":
                System.out.println(this.toString() + " падает с крыши за ненадобностью");
                break;
            case "jam":
                System.out.println("Эффект бесконечного варенья");
        }
    }

    @Override
    public void printStatus(){
        System.out.println("У малыша осталось " + this.getJamCounter() + " банок варенья");
    }

    @Override
    public void rest(){
        if (this.getEndurance() <= RESTMIN){ addEndurance(RESTINC, this); }
        else{ this.setEndurance(MAXENDUR); }
        System.out.println(this.toString() + "немного отдохнул");
    }

    @Override
    public String toString() {
        return "Малыш " + this.getName();
    }
}