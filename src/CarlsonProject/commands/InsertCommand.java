package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

import java.io.PrintStream;

import static CarlsonProject.WindowsArrayList.*;
import static CarlsonProject.UserHandler.*;

public class InsertCommand implements Command {

    private String s;
    private int index;
    private transient PrintStream out;
    private int UserID;


    /**
     * Command to insert element in collection
     * @param s String contains window
     */
    public InsertCommand( String s){
        this.s = s;
        index = Integer.parseInt(s.split(" ", 2)[0]);
        this.s = s.split(" ", 2)[1];
        this.out = System.out;
    }

    @Override
    public void execute(WindowsArrayList windows){
        windows.setOut(out);
        try{
            windows.add(this.index, fromJSONToWindow(
                fromStringToJSONObject(s)));
        } catch (NullPointerException e){
            this.out.println("Get Null Obj");
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
