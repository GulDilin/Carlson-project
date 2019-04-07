package CarlsonProject.plot;

/**
 * class for Carlson
 */
public class Carlson extends Person implements Talkable {

    /**
     * @param WindowMayChoose window, which can be choosen
     * @param MINUSENDUR
     * @param JAMADDENDUR endurance points, which adds from one jam
     * @param jamWasEaten
     * @param JAMTURN Counter for moves before appear ability to eat jam
     * @param DECENDUR Num of endurance for one move
     */
    private int WindowMayChoose;
    private int jamWasEaten;
    final private int JAMTURN;
    final private int JAMADDENDUR;
    final private int MINUSENDUR;
    final private int MAXENDUR;
    private Effect effect;
    private boolean success = false;

    public Carlson(String name){
        super(name, 20);
        this.MAXENDUR = 20;
        this.JAMADDENDUR = 8;
        this.JAMTURN = 3;
        this.MINUSENDUR = 10;
    }

    /**
     *
     * @param windows array with windows
     * window value sets in Move
     */
    public void chooseWindow(Window windows[]) {
        int currentLen = 0;
        for (int i = 0; i < windows.length; i++) {
            if(windows[i].isSpeakFlag()) {
                WindowMayChoose = i;
                System.out.println();
                System.out.println("Из "
                        + Move.getWindows()[WindowMayChoose].getColor().getColorName()
                        + "го окна доносится крик");

                if (currentLen == 0) {
                    currentLen = Math.abs(i - Move.getCurrWindowID());
                }

                if (Math.abs(Move.getCurrWindowID() - i) <= currentLen) {
                    Move.setTargetWindowID(i);
                    currentLen = Math.abs(i - Move.getCurrWindowID());
                    say("chooseWindow");
                    applyEffect(Move.getWindows()[Move.getTargetWindowID()]);
                }
            }
        }
    }

    public int getJamWasEaten(){
        return this.jamWasEaten;
    }

    public int getJAMTURN() {
        return JAMTURN;
    }

    /**
     * method, which do base move
     */
    public void move(){
        if( effect.nextTurn()){ this.applyEffect(Move.getWindows()[Move.getTargetWindowID()]);}
        int change;
        if (Move.getCurrWindowID() != Move.getTargetWindowID()) {
            change = (Move.getCurrWindowID() < Move.getTargetWindowID()) ? 1 : -1;
            Move.setCurrWindowID(Move.getCurrWindowID() + change);
        }
        this.setEndurance(this.getEndurance()-MINUSENDUR);
        if (this.jamWasEaten > 0){
            this.jamWasEaten--;
        }
    }

    public boolean hasSuccess(){
        return this.success;
    }

    public void eatJam(Baby baby){
        if (jamWasEaten == 0) {
            if (baby.hasJam()) {
                baby.decJam();
                this.addEndurance(JAMADDENDUR, this);
                say("jam");
                this.jamWasEaten = JAMTURN;
            }
        }
    }

    public void checkOpenWindow(Window window){
        if (window.isOpenFlag()){
            System.out.println(window.getColor().getColorName() + "е окно открыто");
            searchHole(window);
        } else {
            System.out.println(window.getColor().getColorName() + "е окно закрыто");
            say("sad");
        }
    }

    /**
     * check hole in window
     * @param window window, which will be checked
     */
    public void searchHole(Window window){
        if (window.isHoleFlag()){
            System.out.println(this.toString() + " нашёл дыру в занавесках " + window.getColor().getColorName() + "го окна");
            this.success = true;
        } else {
            System.out.println("Чёрт, дыры в " + window.getColor().getColorName() + "м окне нет");
            say("sad");
        }
    }

    public int getMINUSENDUR() {
        return MINUSENDUR;
    }

    @Override
    public void printStatus(){
        System.out.println("Выносливость " + this.toString() + "равна " + this.getEndurance());
    }

    @Override
    public void rest(){
        final int RESTMIN = 5;
        if (this.getEndurance() <= RESTMIN){ this.setEndurance(this.getEndurance() + RESTMIN ); }
        else{ setEndurance(MAXENDUR); }
        System.out.println(this.toString() + " немного отдохнул");
    }

    @Override
    public void applyEffect(Window window){
        effect = window.getColor().getEffect();
        switch (effect){
            case DECENDUR:
                if (effect.success()) {
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
        if (what == "chooseWindow"){
            System.out.println("Малыш и Карлсон ползут к " + Move.getWindows()[Move.getTargetWindowID()].getColor().getColorName() + "му окну");
            System.out.println("К нему ближе всего");
        } else if (what == "jam"){
            System.out.println("Карлсон съел банку варенья");
            System.out.println("Выносливость " + this.toString() + " повысилась");
        } else if (what == "sad"){
            System.out.println("Карлсон " + this.getName() + " расстроен");
        }
    }

    @Override
    public String toString() {
        return "Карлсон " + this.getName();
    }
}


