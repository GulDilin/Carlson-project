package CarlsonProject;
//abstract class for realise Carlson and Baby
public abstract class Person{

	private String name;
	private int endurance; //выносливость

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

	public Person(String name, int endur){
		this.name = name;
		this.endurance = endur;
	}
