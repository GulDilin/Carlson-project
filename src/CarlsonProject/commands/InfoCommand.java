package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

public class InfoCommand implements Command {
    WindowsArrayList windows;

    /**
     * Comand to print info
     * @param windows collection
     */
    public InfoCommand(WindowsArrayList windows){
        this.windows = windows;
    }

    @Override
    public void execute(){
        System.out.println(windows.getInfo());
    }
}
