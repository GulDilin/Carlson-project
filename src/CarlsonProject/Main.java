package CarlsonProject;

public class Main{
    public static void main(String[] args){
        Carlson carlson = new Carlson("Карл");
        Baby baby = new Baby("Мистер Малыш");

        Move move = new Move(carlson, baby);
        move.go();
    }
}
