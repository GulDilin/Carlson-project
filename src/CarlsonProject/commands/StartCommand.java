package CarlsonProject.commands;

import CarlsonProject.*;
import CarlsonProject.plot.*;
import server.DataBaseManager;

import java.io.PrintStream;

public class StartCommand implements Command{
    private NewBaby baby;
    private NewCarlson carlson;
    private Nurse nurse;
    private NewMove move;
    private WindowsArrayList windows;
    private transient PrintStream out;
    private int UserID;
    private String userHash;
    private DataBaseManager dataBaseManager;

    /**
     * Command to start move part
     * @param nurse Nurse obj
     * @param carlson Carlson obj
     * @param baby Baby obj
     *
     */
    public StartCommand(Nurse nurse,NewCarlson carlson,NewBaby baby){
        this.baby = baby;
        this.carlson = carlson;
        this.nurse = nurse;
        this.out = System.out;
    }

    @Override
    public void setUserID(int userID) {
        UserID = userID;
    }

    @Override
    public void execute(WindowsArrayList windows){
        windows.setOut(out);
        if (windows.size() != 0) {
            try {
                this.move = new NewMove(carlson, baby, nurse, windows.toArray());
                this.move.go();
            } catch (NurseDoesntHide e) {
                this.out.println(e.getMessage());
                this.out.println("Состояние видимости няни: " + nurse.isVisible());
            } catch (NoRobersException | CarlsonAndBabyStatusNotMatch ex){
                this.out.println( ex.getMessage());
            }
        } else {
            this.out.println("No windows in collection");
        }
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
