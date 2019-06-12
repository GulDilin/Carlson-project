package CarlsonProject.commands;

import CarlsonProject.WindowsArrayList;
import CarlsonProject.plot.Window;
import org.json.simple.JSONObject;
import server.DataBaseManager;

import java.io.PrintStream;

import static CarlsonProject.WindowsArrayList.*;
import static CarlsonProject.UserHandler.*;

public class InsertCommand implements Command {

    private String s;
    private int index;
    private transient PrintStream out;
    private int UserID;
    private String userHash;
    private DataBaseManager dataBaseManager;


    /**
     * Command to insert element in collection
     *
     * @param s String contains window
     */
    public InsertCommand(String s) {
        this.s = s;
        index = Integer.parseInt(s.split(" ", 2)[0]);
        this.s = s.split(" ", 2)[1];
        this.out = System.out;
    }

    @Override
    public void setDataBaseManager(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public void execute(WindowsArrayList windows) {
        windows.setOut(out);
        try {
            JSONObject object = fromStringToJSONObject(s, out);
            Window window = fromJSONToWindow(object, out);
            if (window != null) {
                if ((dataBaseManager != null) && (dataBaseManager.addWindow(window, UserID)))

                    windows.add(this.index, window);
            }
        } catch (NullPointerException ex) {
            out.println("Get Null Object");
            ex.printStackTrace();
        }
    }

        @Override
        public void setUserID ( int userID){
            UserID = userID;
        }

        @Override
        public void setOut (PrintStream out){
            this.out = out;
        }

        @Override
        public void setUserHash (String user, String password){
            this.userHash = DataBaseManager.getMD5(user) + DataBaseManager.getMD5(password);
        }

        @Override
        public String getUserHash () {
            return userHash;
        }

        @Override
        public int getUserId () {
            return UserID;
        }
    }
