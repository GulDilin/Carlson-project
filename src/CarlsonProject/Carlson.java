package CarlsonProject;

public class Carlson extends Person implements Talkable{

	private int WindowMayChoose;
	private boolean jamWasEaten;

	Carlson(String name){
		super(name, 60);
	}

	public void chooseWindow(Window Windows[]) {
		int currentLen = 0;
		for (int i = 0; i < Windows.length; i++) {
			If(Windows[i].isSpeakFlag()) {
				WindowMayChoose = i;
				printStatus();

				If (currenLen == 0) {
					currentLen = Math.abs(i - Move.getCurrWindowID());
				}

				If (Math.abs(Move.getCurrWindowID() - i) <= currentLen) {
					Move.setTargetWindowID(i);
					currenLen = Math.Abs(i - Move.getCurrWindowID());
					printStatus();
					say("chooseWindow");
				}
			}
		}
	}

	public void say(String what, Baby baby){
		If (what == "chooseWindow"){
			System.out.println("Малыш и Карлсон ползут к ", Window[Move.getTargetWindowID].getColor(), "му окну");
		} Elseif (what == "jam"){
			System.out.println("Карлсон съел банку варенья");
			System.out.println("У малыша осталось ", baby);

		}
	}

	public void eatJam(Baby baby){
		If (baby.getJamCounter()>)
		baby.decJam();
		if Jam
	}

	public void printStatus(){
		System.out.println("Из ",  Windows[WindowMayChoose].getColor(), "го окна доносится крик");
	}

	@Override
	public void rest(){
		if (this.getEndurance()<=55){ setEndurance(getEndurance()+5); }
		else{ setEndurance(60); }
	}
}

