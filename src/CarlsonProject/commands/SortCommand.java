package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;
import server.DataBaseManager;

import java.io.PrintStream;

public class SortCommand implements Command {

    private transient PrintStream out;
    private int UserID;
    private String userHash;
    private DataBaseManager dataBaseManager;

    /**
     * Command to sort elements
     */
    public SortCommand(){
        this.out = System.out;
    }

    @Override
    public void execute(WindowsArrayList windows){
        windows.setOut(out);
        windows.sort();
    }

    @Override
    public void setDataBaseManager(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public void setUserID(int userID) {
        UserID = userID;
    }

    @Override
    public void setOut(PrintStream out) {
        this.out = out;
    }

    @Override
    public void setUserHash(String user, String password) {
        this.userHash = DataBaseManager.getMD5(user) + DataBaseManager.getMD5(password);
    }

    @Override
    public String getUserHash() {
        return userHash;
    }

    @Override
    public int getUserId() {
        return UserID;

    }

}
