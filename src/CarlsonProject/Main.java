package CarlsonProject;
public class Main {
    public static void main(String[] args) {
        Nurse nurse = new Nurse("Лучшая в мире");
        NewBaby baby = new NewBaby("Мистер", 2);
        NewCarlson carlson = new NewCarlson("В САМОМ РАСЦВЕТЕ СИЛ");

        NewMove move = new NewMove(carlson, baby, nurse);
        try {
            move.go();
        } catch (NurseDoesntHide e) {
            System.out.println(e.getMessage());
            System.out.println("Состояние видимости няни: " + nurse.isVisible());
        }
    }
}
