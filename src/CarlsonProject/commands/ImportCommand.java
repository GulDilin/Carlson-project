package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

public class ImportCommand implements  Command{

    private WindowsArrayList windows;
    private String fileName;

    /**
     * Command to import collection from file
     * @param windows collection
     * @param fileName name of file
     */
    public ImportCommand(WindowsArrayList windows, String fileName){
        this.windows = windows;
        this.fileName = fileName;

    }

    @Override
    public void execute(){
        try{
            windows.importFromFile(fileName);
        } catch (NullPointerException e){
            System.out.println("Get Null Obj");
        }
    }
}
