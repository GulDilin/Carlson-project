public abstract class Person{

	private String name;
	private int endurance;

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

	public Person(String name; int endurance){
		this.name = name;
		this.endurance = endurance;	
	}
