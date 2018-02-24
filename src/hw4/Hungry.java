package hw4;

public class Hungry implements HungerState {

	
	
	
	public String toString(){
		return "Hungry";
	}


	public void SetState(Swimmable animal) {
		animal.setState(this);
		
	}
	
}
