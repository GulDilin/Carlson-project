package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;
import static CarlsonProject.WindowsArrayList.*;

public class InsertCommand implements Command {

    private WindowsArrayList windows;
    private String s;
    private int index;


    /**
     * Command to insert element in collection
     * @param windows collection
     * @param s String contains window
     */
    public InsertCommand(WindowsArrayList windows, String s){
        this.windows = windows;
        this.s = s;
        index = Integer.parseInt(s.split(" ", 2)[0]);
        s = s.split(" ", 2)[1];

    }

    @Override
    public void execute(){
        try{
            windows.add(index, fromJSONToWindow(
                fromStringToJSONObject(s)));
        } catch (NullPointerException e){
            System.out.println("Get Null Obj");
        }
    }
}
