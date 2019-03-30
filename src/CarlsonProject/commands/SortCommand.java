package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

public class SortCommand implements Command {

    private WindowsArrayList windows;

    /**
     * Command to sort elements
     * @param windows collectoins
     */
    public SortCommand(WindowsArrayList windows){
        this.windows = windows;
    }

    @Override
    public void execute(){
        windows.sort();
    }
}
