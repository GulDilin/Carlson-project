package CarlsonProject;

public class Carlson extends Person implements Talkable{

    private int WindowMayChoose;
    private int jamWasEaten;
    // Счётчик ходов до возможности употребить джем
    final private int JAMTURN = 6; // Количество ходов, которые должны пройти между проеданием джема

    Carlson(String name){
        super(name, 60);
    }

    public void chooseWindow(Window windows[]) { //принимает массив всех окон и выбирает из него одно
        // Значение выбранного окна присваивается полю в классе Move
        int currentLen = 0;
        for (int i = 0; i < windows.length; i++) {
            if(windows[i].isSpeakFlag()) {
                WindowMayChoose = i;
                printStatus();

                if (currentLen == 0) {
                    currentLen = Math.abs(i - Move.getCurrWindowID());
                }

                if (Math.abs(Move.getCurrWindowID() - i) <= currentLen) {
                    Move.setTargetWindowID(i);
                    currentLen = Math.abs(i - Move.getCurrWindowID());
                    printStatus();
                    say("chooseWindow");
                }
            }
        }
    }

    public void say(String what, Baby baby){
        if (what == "chooseWindow"){
            System.out.println("Малыш и Карлсон ползут к ", Window[Move.getTargetWindowID()].getColor().toString(), "му окну");
        } else if (what == "jam"){
            System.out.println("Карлсон съел банку варенья");
            System.out.printf("У малыша осталось %d банок варенья",  baby.getJamCounter());

        }
    }

    public int getJamWasEaten(){
        return this.jamWasEaten;
    }

    public void eatJam(Baby baby){
        if (jamWasEaten == 0) {
            if (baby.hasJam()) {
                baby.decJam();
                this.jamWasEaten = JAMTURN;
            }
        }
    }

    public void printStatus(){
        System.out.printf("Из #s го окна доносится крик", Move.getWindows()[WindowMayChoose].toString());
    }

    @Override
    public void rest(){
        if (this.getEndurance()<=55){ this.setEndurance(this.getEndurance()+5); }
        else{ setEndurance(60); }
    }

    public String toString() {
        return "Малыш " + this.getName();
    }
}

