package CarlsonProject;

public class Nurse implements Talkable{
    private String name;
    final private double HEARNESS;
    private boolean isVisible = true;

    public Nurse(String name, double HS){
        this.name = name;
        this.HEARNESS = HS;
    }

    public Nurse(String name){
        this(name, Math.random() * 50);
    }

    public String getName(){
        return this.name;
    }

    public boolean isVisible(){
        return this.isVisible;
    }

    public boolean hear(double volume){
        say("hear");
        if (volume >= this.HEARNESS){
            say("heard");
        }else{
            say("unheard");
        }
        return (volume >= this.HEARNESS);
    }

    public void hide(){
        this.isVisible = false;
        printStatus();
    }

    @Override
    public void say(String what){
        switch (what){
            case "hear":
                System.out.println(this.name + " Няня прислушивается");
                break;

            case "heard":
                System.out.println(this.name + " Няня услышала голоса\nОни были достаточно громкими");
                break;

            case "unheard":
                System.out.println(this.name + " Няня не услышала голоса");
                break;
        }
    }

    @Override
    public void printStatus(){
        if (!isVisible){
            System.out.println(this.name + " Няня спряталась за трубу\n");
        }
    }
}
