package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

public class RemoveLastCommand implements Command {

    private WindowsArrayList windows;

    /**
     * Command to remove last elem from collection
     * @param windows collection
     */
    public RemoveLastCommand(WindowsArrayList windows){
        this.windows = windows;
    }

    @Override
    public void execute(){
        windows.removeLast();
    }
}
