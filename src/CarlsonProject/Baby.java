package CarlsonProject;

final public class Baby extends Person implements Talkable {

    private int jamCounter;

    public Baby(String name){
        super(name, 70);
    }
    public void followCarlson(Carlson carlson){

    }

    public void die(){
        System.out.println("малыш падает с крыши за ненадобностью");
    }

    public void decJam(){
        if (this.jamCounter > 0){
            this.jamCounter--;
        }
    }

    public boolean hasJam(){
        if (this.jamCounter > 0){
            return true;
        }
    }

    @Override
    public void rest(){
        if (this.getEndurance()<=55){ setEndurance(getEndurance()+5); }
        else{ setEndurance(60); }
    }
}