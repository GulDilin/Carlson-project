package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

import java.io.PrintStream;

public class RegisterCommand implements Command {
    private transient PrintStream out;
    private int UserID;

    public RegisterCommand(){
        out = System.out;
    }

    @Override
    public void execute(WindowsArrayList windows){

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
