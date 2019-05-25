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
<<<<<<< HEAD
<<<<<<< HEAD
    public AddCommand(String line){
        System.out.println(line);
=======
    public AddCommand(WindowsArrayList windows, String line) throws NoElementException {
>>>>>>> e9052eb61c13f1c19208975e52ea4f53602f6324
        this.line = line;
        this.out = System.out;
=======
    public AddCommand(WindowsArrayList windows, String line) throws NoElementException {
        this.line = line;
        this.windows = windows;
        if (this.line.equalsIgnoreCase("{}")){
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
