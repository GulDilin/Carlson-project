package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

import java.io.PrintStream;

public class SaveCommand implements Command {

    private String fileName;
    private transient PrintStream out;
    private int UserID;

    /**
     * Command to save to file
     */
    public SaveCommand(String fileName){
        this.fileName = fileName;
        this.out = System.out;
    }

    @Override
    public void execute(WindowsArrayList windows){
        windows.setOut(out);
        windows.save(fileName);
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
