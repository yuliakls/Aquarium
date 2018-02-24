package hw4;

public class Singleton {

	private static Singleton instance = null;
	private boolean Food;
	private Singleton(){
		Food=false;	
	}


	public static Singleton getInstance( ){
		if(instance == null){
			instance = new Singleton();
		}
		return instance;
	}
	
	public void setFood(){
		Food=true;	
		
	}
	
	public boolean getFood(){
		return Food;
	}
	
	public void eatFood(){
		
		Food=false;
	}

}
