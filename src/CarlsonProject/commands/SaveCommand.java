package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

public class SaveCommand implements Command {

    private WindowsArrayList windows;
    private String fileName;

    /**
     * Command to save to file
     * @param windows collection
     */
    public SaveCommand(WindowsArrayList windows, String fileName){
        this.windows = windows;
        this.fileName = fileName;
    }

    @Override
    public void execute(){
        windows.save(fileName);
    }
}
