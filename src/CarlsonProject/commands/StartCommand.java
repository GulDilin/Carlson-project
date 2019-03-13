package CarlsonProject.commands;

import CarlsonProject.*;

public class StartCommand implements Command{
    private NewBaby baby;
    private NewCarlson carlson;
    private Nurse nurse;
    private NewMove move;

    public StartCommand(Nurse nurse,NewCarlson carlson,NewBaby baby, NewMove move){
        this.baby = baby;
        this.carlson = carlson;
        this.move = move;
        this.nurse = nurse;
    }

    @Override
    public void execute(){
        try {
            move.go();
        } catch (NurseDoesntHide e) {
            System.out.println(e.getMessage());
            System.out.println("Состояние видимости няни: " + nurse.isVisible());
        }
    }

}
