package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

public class ShowCommand implements Command {

    private WindowsArrayList windows;

    /**
     * Command to show elements
     * @param windows collection
     */
    public ShowCommand(WindowsArrayList windows){
        this.windows = windows;
    }

    @Override
    public void execute() {
        windows.show();
    }
}
