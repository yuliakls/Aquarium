package hw4;
import java.awt.Color;

public class Memento {  ///both fish jelly and seaplant
	private SeaCreature creature;
	private Color colorr;
	private int size;
	private int x;
	private int y;
	private int h_speed;
	private int v_speed;
	private int foodcount,Frequency;

	public Memento(SeaCreature creature){ 
		this.creature  = creature ; 
		if(creature.getType()=="Swimmable"){
			colorr=((Swimmable)creature).getColor();
			size=((Swimmable)creature).getSize();
			x=((Swimmable)creature).getLoc_x();
			y=((Swimmable)creature).getLoc_y();
			h_speed=((Swimmable)creature).getHorSpeed();
			v_speed=((Swimmable)creature).getVerSpeed();
			Frequency=((Swimmable)creature).getFoodFrequency();
			foodcount=((Swimmable)creature).getEatCount();
		}
		else{			
			size=((Immobile)creature).getSize();
			x=((Immobile)creature).getLoc_x();
			y=((Immobile)creature).getLoc_y();
		}
	}
	public SeaCreature getState() {
		
		
		if(creature.getType()=="Swimmable"){
			
			creature.setSize(size);
			((MarineAnimal)creature).PaintFish(colorr);
			((Swimmable)creature).setLoc_x(x);
			((Swimmable)creature).setLoc_y(y);
			((Swimmable)creature).setVerSpeed(v_speed);
			((Swimmable)creature).setHorSpeed(h_speed);
			((Swimmable)creature).setFeedFrequency(Frequency);
			((Swimmable)creature).setfoodcount(foodcount);
			
		}
		else{
			((Immobile)creature).setSize(size);
			((Immobile)creature).setLoc_y(y);
			((Immobile)creature).setLoc_x(x);			
		}
		

		return creature; 
		
	} 


}
