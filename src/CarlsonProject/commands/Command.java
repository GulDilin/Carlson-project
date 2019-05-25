package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;

import java.io.PrintStream;
import java.io.Serializable;

public interface Command extends Serializable {
    void execute(WindowsArrayList windows);
    void setOut(PrintStream out);
    void setUserID(int ID);
}
