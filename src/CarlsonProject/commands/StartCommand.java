package CarlsonProject.commands;

import CarlsonProject.*;
import CarlsonProject.plot.*;

public class StartCommand implements Command{
    private NewBaby baby;
    private NewCarlson carlson;
    private Nurse nurse;
    private NewMove move;
    private WindowsArrayList windows;

    /**
     * Command to start move part
     * @param nurse Nurse obj
     * @param carlson Carlson obj
     * @param baby Baby obj
     *
     */
    public StartCommand(WindowsArrayList windows, Nurse nurse,NewCarlson carlson,NewBaby baby){
        this.baby = baby;
        this.carlson = carlson;
        this.move = new NewMove(carlson, baby, nurse, windows.toArray());
        this.nurse = nurse;
        this.windows = windows;
    }

    @Override
    public void execute(){
        if (windows.size() != 0) {
            try {
                move.go();
            } catch (NurseDoesntHide e) {
                System.out.println(e.getMessage());
                System.out.println("Состояние видимости няни: " + nurse.isVisible());
            } catch (NoRobersException ex){
                System.out.println( ex.getMessage());
            } catch (CarlsonAndBabyStatusNotMatch exx){
                System.out.println( exx.getMessage());
            }
        } else {
            System.out.println("No windows in collection");
        }
    }

}
