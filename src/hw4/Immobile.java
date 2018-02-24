package hw4;



public abstract class Immobile implements SeaCreature {

	
	String name;
	abstract public int getLoc_x();
	abstract public int getLoc_y();
	abstract public void setLoc_x(int x);
	abstract public void setLoc_y(int y);
	abstract public int getSize();
	abstract public void setSize(int size);
	
}
