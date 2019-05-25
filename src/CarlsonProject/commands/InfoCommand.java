package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

import java.io.PrintStream;

public class InfoCommand implements Command {
    private transient PrintStream out;
    private int UserID;

    /**
     * Comand to print info
     *
     */
    @Override
    public void execute(WindowsArrayList windows){
        windows.setOut(out);
        this.out.println(windows.getInfo());
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
