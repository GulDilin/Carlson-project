package CarlsonProject;

import com.sun.xml.internal.bind.v2.model.core.ID;

final public static class Move {
    private static Window[] windows;
    private static int currWindowID;
    private static int targetWindowID;

    public static int getCurrWindowID(){
        return this.currWinwowID;
    }

    public static int getTargetWindowID(){
        return this.targetWindowID;
    }

    public static void setCurrWindowID(int ID){
        this.currWinwowID = ID;
    }

    public static void setTargetWindowID(int ID){
        this.targetWindowID = ID;
    }

    public static Window[] getWindows(){
        return windows;
    }
}