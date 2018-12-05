package CarlsonProject;
//abstract class for realise Carlson and Baby
public abstract class Person implements EnduranceChanger{

	private String name;
	private int endurance; //выносливость

    public Person(String name, int endur){
        this.name = name;
        this.endurance = endur;
    }

    public void move(){
	}
	
	public String getName(){
		return name;
	}

	public int getEndurance(){
		return endurance;
	}

	public void setEndurance(int endur){
		this.endurance = endur;
	}

	public abstract void rest(){};

	@Override
    public void decEndurance(int points, Person person){
	    person.endurance -= points;
	}

    public void incEndurance(int points, Person person){
        person.endurance += points;
    }

}
