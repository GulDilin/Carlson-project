package CarlsonProject;

public class Carlson extends Person implements Talkable{

    private int WindowMayChoose;
    private int jamWasEaten;
    final private int JAMTURN;
    final private int JAMADDENDUR;
    final private int MINUSENDUR;
    private Effect effect;
    private boolean success = false;
    /*
        * @param WindowMayChoose
        * @param jamWasEaten
        * @param JAMTURN Счётчик ходов до возможности употребить джем
        * @param DECENDUR Определяет на сколько на движение уменьшается выносливость
        *
        *
     */
    public Carlson(String name){
        super(name, 60);
        this.JAMADDENDUR = 20;
        this.JAMTURN = 6;
        this.MINUSENDUR = 7;
    }

    public void chooseWindow(Window windows[]) {
        //принимает массив всех окон и выбирает из него одно
        // Значение выбранного окна присваивается полю в классе Move
        int currentLen = 0;
        for (int i = 0; i < windows.length; i++) {
            if(windows[i].isSpeakFlag()) {
                WindowMayChoose = i;
                System.out.printf("Из #s го окна доносится крик", Move.getWindows()[WindowMayChoose].getColor().toString());

                if (currentLen == 0) {
                    currentLen = Math.abs(i - Move.getCurrWindowID());
                }

                if (Math.abs(Move.getCurrWindowID() - i) <= currentLen) {
                    Move.setTargetWindowID(i);
                    currentLen = Math.abs(i - Move.getCurrWindowID());
                    System.out.printf("Из #s го окна доносится крик", Move.getWindows()[WindowMayChoose].getColor().toString());
                    say("chooseWindow");
                    applyEffect(Move.getWindows()[Move.getTargetWindowID()]);
                }
            }
        }
    }

    public int getJamWasEaten(){
        return this.jamWasEaten;
    }


    //метод, осуществляющий движение между окнами
    public void move(){
        this.effect.nextTurn();
        int change = 0;
        if (Move.getCurrWindowID() != Move.getTargetWindowID()) {
            change = (Move.getCurrWindowID() < Move.getTargetWindowID()) ? 1 : -1;
            Move.setCurrWindowID(Move.getCurrWindowID() + change);
        }
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
            System.out.println(window.getColor().toString() + "е окно открыто");
            searchHole(window);
        } else {
            System.out.println(window.getColor().toString() + "е окно закрыто");
            say("sad");
        }
    }

    // Проверяет есть ли дыра в окне
    public void searchHole(Window window){
        if (window.isHoleFlag()){
            System.out.println(this.toString() + " нашёл дыру в занавесках " + window.getColor().toString() + "го окна");
            this.success = true;
        } else {
            System.out.println("Чёрт, дыры в " + window.getColor().toString() + "м окне нет");
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
        if (this.getEndurance()<=55){ this.setEndurance(this.getEndurance()+5); }
        else{ setEndurance(60); }
    }

    @Override
    public void applyEffect(Window window){
        effect = window.getColor().getEffect();
        switch (effect){
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
                }
                break;
        }
    }

    @Override
    public void say(String what){
        if (what == "chooseWindow"){
            System.out.println("Малыш и Карлсон ползут к " + Move.getWindows()[Move.getTargetWindowID()].getColor().toString() + "му окну");
        } else if (what == "jam"){
            System.out.println("Карлсон съел банку варенья");
            System.out.println("Выносливость " + this.toString() + "повысилась");
        } else if (what == "sad"){
            System.out.println("Карлсон " + this.getName() + " расстроен");
        }
    }

    @Override
    public String toString() {
        return "Карлсон " + this.getName();
    }
}


