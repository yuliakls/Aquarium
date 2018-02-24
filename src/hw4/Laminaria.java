package hw4;

import java.awt.Color;
import java.awt.Graphics;

public class Laminaria extends Immobile{

	private Color colorr=Color.green;
	private int y,x,size;
	private AquaFrame My_Frame;
	
	public Laminaria() {

		
		
		

	}
	
	public void drawCreature(Graphics g) {
		g.setColor(colorr);
		
		g.fillArc(x-size/20, y-size/20, size/10, size*4/5, 0, 360);
		g.fillArc(x-size*3/20,y-size*13/15,size/10,size*2/3,0,360);
		g.fillArc(x+size/20, y-size*13/15, size/10, size*2/3,0, 360);
		g.drawLine(x, y, x, y-size/5);
		g.drawLine(x, y, x-size/10, y-size/5);
		g.drawLine(x, y, x+size/10, y-size/5);
	}

	public String getType() {
		return "Immobile";
	}
	
	public void setSize(int size){this.size=size;}

	
	public void addFrame(AquaFrame frame) {
		this.My_Frame=frame;
		
		x=(int)(Math.random()*10000)%(My_Frame.getWidth());
		
		y=My_Frame.getHeight()-size;

		
	}
	public int getLoc_x(){return x;}
	public int getLoc_y(){return y;}


	public Color getColor() {
		return colorr;	
	}


	public int getSize() {
		return size;	
	}

	
	public void setLoc_x(int x) {
		this.x=x;
		
	}


	public void setLoc_y(int y) {
		this.y=y;
		
	}


	public String getData() {
		
		return name+"-Size:"+getSize()+", Color: green .";
	}
	
	

}
