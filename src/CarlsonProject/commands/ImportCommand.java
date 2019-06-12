package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;
import server.DataBaseManager;

import java.io.PrintStream;

public class ImportCommand implements  Command{
    private String fileName;
    private transient PrintStream out;
    private int UserID;
    private String userHash;
    private DataBaseManager dataBaseManager;



    /**
     * Command to import collection from file
     * @param fileName name of file
     */
    public ImportCommand(String fileName){
        this.fileName = fileName;

    }

    @Override
    public void setDataBaseManager(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public void execute(WindowsArrayList windows){
        windows.setOut(out);
        try{
            windows.importFromFile(fileName);
        } catch (NullPointerException e){
            out.println("Get Null Obj");
        }
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
