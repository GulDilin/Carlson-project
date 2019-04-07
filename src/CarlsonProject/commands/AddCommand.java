package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;
import static CarlsonProject.WindowsArrayList.*;

public class AddCommand implements Command {

    /**
     * Command, which realise add new elem to collection from user input
     */
    private String line;
    private WindowsArrayList windows;

    /**
     *
     * @param line string with window obj
     * @param windows collection
     *
     */
    public AddCommand(WindowsArrayList windows, String line) throws NoElementException {
        this.line = line;
        this.windows = windows;
        if (this.line.equalsIgnoreCase("{}")){
            throw new NoElementException("No element error");
        }
    }

    @Override
    public void execute(){
        try{
            if (fromJSONToWindow(fromStringToJSONObject(line)) != null) {
                windows.add(fromJSONToWindow(fromStringToJSONObject(line)));
            }
        } catch (NullPointerException e){
            System.out.println("Get Null Obj");
        }
    }
}
