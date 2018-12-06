package CarlsonProject;

final public static class Move implements Talkable {
    private static Window[] windows = {new Window(Color.RED), new Window(Color.GREEN), new Window(Color.BLUE), new Window(Color.YELLOW)};
    private static int currWindowID;
    private static int targetWindowID = -1; // -1 обозначает, что окно ещё не выбрано;
    private Carlson carlson;
    private Baby baby;

    public Move(Carlson carlson, Baby baby, int ID) {
        this.carlson = carlson;
        this.baby = baby;
        Move.currWindowID = ID;
    }

    public Move(Carlson carlson, Baby baby){
        this( carlson, baby, (int)(Math.random() * windows.length));
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

    public static Window[] getWindows(){
        return windows;
    }

    //метод, который производит всё действие
    public void go(){
        do{
            printStatus();
            this.carlson.chooseWindow(Move.getWindows());
            if (targetWindowID != -1){

            }
        } while ((currWindowID != -1) & (currWindowID != targetWindowID));

        say("success");
    }

    @Override
    public void printStatus(){
        System.out.println();
        System.out.println(targetWindowID == -1 ? "Окно - цель не выбрано" : "Малыш и карлсон ползут к " + Move.getTargetWindowID());
    }

    public void say(String what){
        switch (what){
            case "success":
                System.out.println("Карлсон посмотрел в окно и всё увидел, профит!");
                break;
        }
    }
}