package CarlsonProject;
public class NewMove{
    private Nurse nurse;
    private NewCarlson carlson;
    private NewBaby baby;
    private Move move;
    private Window[] windows;

    public NewMove(NewCarlson carlson, NewBaby baby, Nurse nurse, Window[] windows){
        this.nurse = nurse;
        this.carlson = carlson;
        this.baby = baby;
        this.windows = windows;
        this.move = new Move(carlson, baby, windows);
    }

    public NewMove(NewCarlson carlson, NewBaby baby, Nurse nurse) {
        this.nurse = nurse;
        this.carlson = carlson;
        this.baby = baby;
        this.move = new Move(carlson, baby);
        this.windows = move.getWindows();
    }



    public Nurse getNurse() {
        return nurse;
    }

    public NewCarlson getCarlson() {
        return carlson;
    }

    public NewBaby getBaby() {
        return baby;
    }

    Calculateable average = (carl, bab) -> (carlson.getSpeakVolume() + baby.getSpeakVolume()) / 2;

    public void go() throws NurseDoesntHide, CarlsonAndBabyStatusNotMatch, NoRobersException {
        if (nurse.hear(average.calculate(this.getCarlson(), this.getBaby()))) {
            nurse.hide();
        } else {
            throw new NurseDoesntHide( nurse.getName() + " Няня не спряталась до прихода Малыша с Карлсоном");
        }
        if ((carlson.talk()) & (baby.talk())) {
            class RobberMove {
                private Robber robber1;
                private Robber robber2;

                class Robber {
                    private String name;
                    private int likeRob;

                    Robber(String name, int percent) {
                        this.name = name;
                        this.likeRob = percent;
                    }

                    Robber(String name) {
                        this(name, (int) (Math.random() * 100));
                    }

                    public String getName() {
                        return "Товарищ " + this.name;
                    }

                    public boolean lookLikeARob() {
                        System.out.println("Товарищ " + this.name + " выглядит как " +
                                (this.likeRob > 50 ? "жулик" : "типичный товарищ") + "\n");
                        return this.likeRob > 50;
                    }
                }

                public RobberMove() {
                    robber1 = new Robber("Черный");
                    robber2 = new Robber("Белый");
                }

                void go() {
                    System.out.println("Малыш и карлсон выбираются на мансарду \n");
                    move.go();
                    System.out.println("\n\nМалыш " + baby.toString() + " тоже смотрит в дыру\n");
                    System.out.println("В наличии два субъекта: " + robber2.getName() + " и " + robber1.getName());
                    if ((robber1.lookLikeARob()) & (robber2.lookLikeARob())) {
                        System.out.println("Субъекты явно напоминают жуликов");
                    } else {
                        System.out.println("Не все субъекты похожи на жуликов");
                    }
                }
            }
            if (this.move.getWindows()[this.move.getCurrWindowID()].isRobberFlag()) {
                RobberMove robMove = new RobberMove();
                robMove.go();
            } else {
                throw new NoRobersException("Воришек не обнаружено. Провал");
            }
        } else{
            throw new CarlsonAndBabyStatusNotMatch("Состояния разговора о жуликах между " + carlson.toString() + " и " + " Малыша " + baby.getName() + " не совпадают");
        }
    }


}

@FunctionalInterface
interface Calculateable {
     double calculate(NewCarlson carl, NewBaby baby);
}




