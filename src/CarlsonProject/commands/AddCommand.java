package CarlsonProject.commands;

import CarlsonProject.Window;
import CarlsonProject.WindowsArrayList;

public class AddCommand implements Command {
    private String line;
    private WindowsArrayList windows;

    public AddCommand(String line, WindowsArrayList windows){
        this.line = line;
        this.windows = windows;
    }

    @Override
    public void execute(){
        windows.add(WindowsArrayList.fromJSONToWindow(WindowsArrayList.fromStringToJSONString(line)));
    }
}
