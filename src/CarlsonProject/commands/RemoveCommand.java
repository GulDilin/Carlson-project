package CarlsonProject.commands;


import CarlsonProject.WindowsArrayList;
import CarlsonProject.plot.Window;
import org.json.simple.JSONObject;
import server.DataBaseManager;

import java.io.PrintStream;

import static CarlsonProject.WindowsArrayList.fromJSONToWindow;
import static CarlsonProject.WindowsArrayList.fromStringToJSONObject;

public class RemoveCommand implements Command {

    private String s;
    private transient PrintStream out;
    private int UserID;
    private String userHash;
    private DataBaseManager dataBaseManager;

    /**
     * Command to remove elem from collection
     * @param s String with elem
     */
    public RemoveCommand( String s){
        this.s = s;
        this.out = System.out;
    }

    @Override
    public void setDataBaseManager(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public void execute(WindowsArrayList windows){
        windows.setOut(out);
        try{
        JSONObject object = fromStringToJSONObject(s, out);
        Window window = fromJSONToWindow(object, out);
//        dataBaseManager.removeWindow(window, UserID, userHash);
            if (window != null) {
                if ((dataBaseManager != null) && (dataBaseManager.removeWindow(window, UserID, userHash)))
                    windows.remove(window);
            }
        } catch (NullPointerException ex) {
            out.println("Get Null Object");
            ex.printStackTrace();
        }
    }

    @Override
    public void setUserID(int userID) {
        UserID = userID;
    }

    @Override
    public void setOut(PrintStream out) {
        this.out = out;
        this.dataBaseManager.setOut(out);
    }

    @Override
    public void setUserHash(String user, String password){
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
