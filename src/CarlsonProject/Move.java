package CarlsonProject;

import com.sun.xml.internal.bind.v2.model.core.ID;

final public static class Move {
    private static Window[] windows;
    private static int currWindowID;
    private static int targetWindowID;

    public Move(Window[] windows) {
        Move.windows = windows;
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

    public void
}