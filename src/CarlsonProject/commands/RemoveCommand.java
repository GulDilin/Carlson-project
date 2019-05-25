package CarlsonProject.commands;


import CarlsonProject.WindowsArrayList;

import java.io.PrintStream;

public class RemoveCommand implements Command {

    private String s;
    private transient PrintStream out;
    private int UserID;

    /**
     * Command to remove elem from collection
     * @param s String with elem
     */
    public RemoveCommand( String s){
        this.s = s;
        this.out = System.out;
    }

    @Override
    public void execute(WindowsArrayList windows){
        windows.setOut(out);
        windows.remove(WindowsArrayList.fromJSONToWindow(
                WindowsArrayList.fromStringToJSONObject(s)));
    }

    @Override
    public void setUserID(int userID) {
        UserID = userID;
    }

    @Override
    public void setOut(PrintStream out) {
        this.out = out;
    }
}
