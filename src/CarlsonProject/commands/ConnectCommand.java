package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

import java.io.PrintStream;

public class ConnectCommand implements Command {
    private WindowsArrayList windows;
    private transient PrintStream out;
    private int UserID;


    public ConnectCommand(){
        this.out = System.out;
    }

    @Override
    public void execute(WindowsArrayList windows) {
        this.windows = windows;
        this.windows.setOut(out);
        out.println("Connected");
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
