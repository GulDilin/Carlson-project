package CarlsonProject.commands;


import CarlsonProject.WindowsArrayList;

public class RemoveCommand implements Command {

    private WindowsArrayList windows;
    private String s;

    /**
     * Command to remove elem from collection
     * @param windows collection
     * @param s String with elem
     */
    public RemoveCommand(WindowsArrayList windows, String s){
        this.windows = windows;
        this.s = s;
    }

    @Override
    public void execute(){
        windows.remove(WindowsArrayList.fromJSONToWindow(
                WindowsArrayList.fromStringToJSONObject(s)));

    }
}
