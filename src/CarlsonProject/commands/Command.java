package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;
import server.DataBaseManager;

import java.io.PrintStream;
import java.io.Serializable;

public interface Command extends Serializable {
    void execute(WindowsArrayList windows);
    void setOut(PrintStream out);
    void setUserID(int ID);
    void setUserHash(String user, String password);
    int getUserId();
    String getUserHash();
    void setDataBaseManager(DataBaseManager dataBaseManager);
}
