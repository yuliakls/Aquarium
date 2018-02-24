package hw4;

public class Originator { 
	private SeaCreature creature; 
	public void setState(SeaCreature creature) { this.creature = creature; } 
	public SeaCreature getState() { return creature; } 
	public Memento createMemento() { 
		return new Memento(creature); 
	} 
	public void setMemento(Memento memento) { 
		creature = memento.getState(); 
	} 
}

