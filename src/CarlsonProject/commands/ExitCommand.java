package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

import java.io.PrintStream;

public class ExitCommand implements Command {
    private transient PrintStream out;
    private int UserID;

    public ExitCommand(){
        this.out = System.out;
    }

    @Override
    public void execute(WindowsArrayList windows) {
        windows.setOut(out);
        windows.save();
        this.out.println("Exit");
    }

    @Override
    public void setOut(PrintStream out) {
        this.out = out;
    }

    @Override
    public void setUserID(int userID) {
        UserID = userID;
    }

    public boolean isExit() {
        return true;
    }
}
