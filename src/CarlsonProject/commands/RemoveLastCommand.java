package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

import java.io.PrintStream;

public class RemoveLastCommand implements Command {

    private transient PrintStream out;
    private int UserID;

    /**
     * Command to remove last elem from collection
     */
    public RemoveLastCommand(){
        this.out = System.out;
    }

    @Override
    public void execute(WindowsArrayList windows){
        windows.setOut(out);
        windows.removeLast();
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
