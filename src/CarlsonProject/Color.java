package CarlsonProject;

public enum Color{
    GREEN,
    BLUE,
    RED,
    YELLOW;

    Effect effect;

    private Color(){
        switch(this){
            case RED:
                effect = new Effect() {

                }
        }
    }
}