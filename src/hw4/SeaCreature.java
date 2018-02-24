package hw4;

import java.awt.Graphics;

public interface SeaCreature {
	public void drawCreature(Graphics g);
	public String getType();
	public void setSize(int size);
	public void addFrame(AquaFrame frame);
	abstract public String getData();
}
