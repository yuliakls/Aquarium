package hw4;

public class Satiated implements HungerState {

	
	
	public String toString(){
		return "Satiated";
	}


	public void SetState(Swimmable animal) {
		animal.setState(this);
		
	}
	
}
