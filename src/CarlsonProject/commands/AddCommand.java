package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

import java.io.PrintStream;

import static CarlsonProject.WindowsArrayList.*;

public class AddCommand implements Command {

    /**
     * Command, which realise add new elem to collection from user input
     */
    private String line;
    private transient PrintStream out;
    private int UserID;

    /**
     *
     * @param line string with window obj
     *
     */
    public AddCommand(String line){
        System.out.println(line);
    }

    @Override
    public void execute(WindowsArrayList windows){
        windows.setOut(out);
        try{
            if (fromJSONToWindow(fromStringToJSONObject(line)) != null) {
                windows.add(fromJSONToWindow(fromStringToJSONObject(line)));
            }
        } catch (NullPointerException e){
            out.println("Get Null Obj");
        }
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
