package hw4;

/**
 * @author      YULIA 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.CyclicBarrier;

public abstract class Swimmable extends Thread implements SeaCreature{
	/**
	 * this is an abstract class creating a new swimmable class, representing the sea animals 
	 */
	protected int horSpeed;
	protected int verSpeed;

	public Swimmable() {
		horSpeed = 0;
		verSpeed = 0;
	}
	public Swimmable(int hor, int ver) {
		horSpeed = hor;
		verSpeed = ver;
	}
	public int getHorSpeed() { return horSpeed; }
	public int getVerSpeed() { return verSpeed; }
	public void setHorSpeed(int hor) { horSpeed  = hor; }
	public void setVerSpeed(int ver) { verSpeed  = ver; }

	abstract public String getAnimalName();
	abstract public void drawAnimal(Graphics g);
	abstract public void setSuspend();
	abstract public void setResume();
	abstract public void setBarrier(CyclicBarrier b);
	abstract public int getSize();
	abstract public void eatInc();
	abstract public int getEatCount();
	abstract public Color getColor();
	abstract public void setSpeed(int x,int y);
	abstract public void addFrame(AquaFrame frame);
	abstract public String getData();
	abstract public void setFeedFrequency(int num);
	abstract public void setState(HungerState state);
	abstract public String getStateName();
	abstract public Swimmable Clone ();
	abstract public int getFoodFrequency();
	abstract public String getColorRGB() ;
	abstract public int getLoc_x();
	abstract public int getLoc_y();
	abstract public void setLoc_x(int x);
	abstract public void setLoc_y(int y);
	abstract public void setfoodcount(int num);
}

