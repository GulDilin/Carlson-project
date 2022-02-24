package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;
import CarlsonProject.plot.Window;
import org.json.simple.JSONObject;
import server.DataBaseManager;

import java.io.PrintStream;

import static CarlsonProject.WindowsArrayList.*;

public class AddCommand implements Command {

    /**
     * Command, which realise add new elem to collection from user input
     */
    private final String line;
    private transient PrintStream out;
    private int userID;
    private String userHash;
    private DataBaseManager dataBaseManager;

    /**
     * @param line string with window obj
     */
    public AddCommand(String line) {
        this.out = System.out;
        this.line = line;
    }

    public AddCommand(String line, int userID) {
        this.out = System.out;
        this.line = line;
        this.userID = userID;
    }

    @Override
    public void execute(WindowsArrayList windows) {
        windows.setOut(out);
        System.out.println("line: " + line);
        try {
            JSONObject object = fromStringToJSONObject(line, out);
            Window window = fromJSONToWindow(object, out);
            window.setOwnerID(userID);
            if (window != null) {
                if ((dataBaseManager != null) && (dataBaseManager.addWindow(window, userID)))

                    windows.add(window);
            }
        }catch (NullPointerException ex){
            out.println("Get Null Object");
            ex.printStackTrace();
        }
    }

    @Override
    public void setUserID(int userID) {
        this.userID = userID;
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
        return userID;
    }

    @Override
    public void setDataBaseManager(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }
}

