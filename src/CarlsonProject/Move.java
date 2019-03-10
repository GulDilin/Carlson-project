package CarlsonProject;

public class Move implements Talkable {
    private static Window[] windows = {
            new Window.Builder(Color.RED).build(),
            new Window.Builder(Color.GREEN).build(),
            new Window.Builder(Color.BLUE).build(),
            new Window.Builder(Color.YELLOW).build()
    };
    private static int currWindowID;
    private static int targetWindowID = -1; // -1 обозначает, что окно ещё не выбрано;
    private Carlson carlson;
    private Baby baby;

    public Move(Carlson carlson, Baby baby){
        this.carlson = carlson;
        this.baby = baby;
        Move.currWindowID = (int)(Math.random() * windows.length);
    }

    public Move(Carlson carlson, Baby baby, int ID, Window[] windows) {
        this.carlson = carlson;
        this.baby = baby;
        Move.currWindowID = ID;
        this.windows = windows;
    }

    public Move(Carlson carlson, Baby baby, Window[] windows){
        this( carlson, baby, (int)(Math.random() * windows.length), windows);
    }


    public static void setCurrWindowID(int ID){
        Move.currWindowID = ID;
    }

    public static void setTargetWindowID(int ID){
        Move.targetWindowID = ID;
    }

    public static int getCurrWindowID(){
        return Move.currWindowID;
    }

    public static int getTargetWindowID(){
        return Move.targetWindowID;
    }

//    public Carlson getCarlson() {
//        return carlson;
//    }
//
//    public Baby getBaby() {
//        return baby;
//    }

    public static Window[] getWindows(){
        return windows;
    }

    //метод, который производит всё действие
    public void go(){
        System.out.println("Список окон:");
        for(Window window: windows){
            System.out.println(window.getColor().toString()+"е");
        }
        System.out.println();

        do{
            for (Window window: windows){
                window.setFlags();
            }
            printStatus();
            this.carlson.chooseWindow(Move.getWindows());
            if (targetWindowID != -1){
                if ((carlson.getEndurance() > carlson.getMINUSENDUR()) & (baby.getEndurance() > baby.getMINUSENDUR())){
                    carlson.move();
                    baby.move();
                    System.out.println();
                    printStatus();
                } else {
                    System.out.println("Недостаточно сил");
                    carlson.eatJam(baby);
                    if (carlson.getJamWasEaten() == carlson.getJAMTURN()){
                        baby.printStatus();
                    } else {
                        carlson.rest();
                        baby.rest();
                    }

                }
                if (currWindowID == targetWindowID){
                    carlson.checkOpenWindow(windows[currWindowID]);
                }
            } else {
                carlson.chooseWindow(windows);
            }
            System.out.println();
        } while ((currWindowID != -1) & (!carlson.hasSuccess()));

        say("success");
        baby.die();
    }

    @Override
    public void printStatus(){
        System.out.println(this.carlson.toString() + " и " + this.baby.toString() + " y " + windows[currWindowID].getColor().toString()+ "го окна");
        System.out.println(targetWindowID == -1 ? "Окно - цель не выбрано" : "Малыш и карлсон ползут к " + windows[targetWindowID].getColor().toString() + "му окну");
        System.out.println("У  " + baby.toString() + " осталось " + baby.getEndurance() + " выносливости");
        System.out.println("У  " + carlson.toString() + " осталось " + carlson.getEndurance() + " выносливости");
    }

    @Override
    public void say(String what){
        switch (what){
            case "success":
                System.out.println("Карлсон посмотрел в окно и всё увидел, профит!");
                break;
        }
    }
}