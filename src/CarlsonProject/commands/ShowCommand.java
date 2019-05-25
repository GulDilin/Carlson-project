package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

import java.io.PrintStream;

public class ShowCommand implements Command {

    private transient PrintStream out;
    private int UserID;

    /**
     * Command to show elements
     */
    public ShowCommand(){
        this.out = System.out;
    }

    @Override
    public void execute(WindowsArrayList windows) {
        windows.setOut(out);
        windows.show();
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
