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
<<<<<<< HEAD
<<<<<<< HEAD
    public InsertCommand( String s){
        this.s = s;
        index = Integer.parseInt(s.split(" ", 2)[0]);
        this.s = s.split(" ", 2)[1];
        this.out = System.out;

=======
    public InsertCommand(WindowsArrayList windows, String s) throws NoElementException {
        this.windows = windows;
        this.s = s;
=======
    public InsertCommand(WindowsArrayList windows, String s) throws NoElementException {
        this.windows = windows;
        this.s = s;
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
        this.index = Integer.parseInt(this.s.split(" ", 2)[0]);
        this.s = fromUserStringToJSONString(this.s.split(" ", 2)[1]);
        if (this.s.equalsIgnoreCase("{}")){
            throw new NoElementException("No element error");
        }
<<<<<<< HEAD
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
=======
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
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
