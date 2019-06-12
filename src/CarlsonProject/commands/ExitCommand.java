package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;
import server.DataBaseManager;

import java.io.PrintStream;

public class ExitCommand implements Command {
    private transient PrintStream out;
    private int UserID;
    private String userHash;
    private DataBaseManager dataBaseManager;

    public ExitCommand(){
        this.out = System.out;
    }

    @Override
    public void execute(WindowsArrayList windows) {
        windows.setOut(out);
        windows.save();
        this.out.println("Exit");
    }

    @Override
    public void setDataBaseManager(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public void setOut(PrintStream out) {
        this.out = out;
    }

    @Override
    public void setUserID(int userID) {
        UserID = userID;
    }

    public boolean isExit() {
        return true;
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
