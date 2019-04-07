package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;
import static CarlsonProject.WindowsArrayList.*;
import static CarlsonProject.UserHandler.*;

public class InsertCommand implements Command {

    private WindowsArrayList windows;
    private String s;
    private int index;


    /**
     * Command to insert element in collection
     * @param windows collection
     * @param s String contains window
     */
    public InsertCommand(WindowsArrayList windows, String s) throws NoElementException {
        this.windows = windows;
        this.s = s;
        this.index = Integer.parseInt(this.s.split(" ", 2)[0]);
        this.s = fromUserStringToJSONString(this.s.split(" ", 2)[1]);
        if (this.s.equalsIgnoreCase("{}")){
            throw new NoElementException("No element error");
        }
    }

    @Override
    public void execute(){
        try{
            windows.add(this.index, fromJSONToWindow(
                fromStringToJSONObject(s)));
        } catch (NullPointerException e){
            System.out.println("Get Null Obj");
        }
    }
}
