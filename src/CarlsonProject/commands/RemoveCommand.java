package CarlsonProject.commands;


import CarlsonProject.WindowsArrayList;

import java.io.PrintStream;

public class RemoveCommand implements Command {

    private String s;
    private transient PrintStream out;
    private int UserID;

    /**
     * Command to remove elem from collection
     * @param s String with elem
     */
<<<<<<< HEAD
<<<<<<< HEAD
    public RemoveCommand( String s){
        this.s = s;
        this.out = System.out;
=======
=======
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
    public RemoveCommand(WindowsArrayList windows, String s) throws NoElementException {
        this.windows = windows;
        this.s = s;
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
        windows.remove(WindowsArrayList.fromJSONToWindow(
                WindowsArrayList.fromStringToJSONObject(s)));
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
