package hw4;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Zostera extends Immobile{

	
	private Color colorr=Color.green;
	private int y,x,size;
	private AquaFrame My_Frame;
	
	
	public Zostera() {
		
		y=485;
		
	}
	
	
	public void drawCreature(Graphics g) {
		
						
		Graphics2D g2=(Graphics2D)g;
		g2.setStroke(new BasicStroke(3));
		g2.setColor(colorr);
		g.drawLine(x, y, x, y-size);
		g.drawLine(x-2, y, x-10, y-size*9/10);
		g.drawLine(x+2, y, x+10, y-size*9/10);
		g.drawLine(x-4, y, x-20, y-size*4/5);
		g.drawLine(x+4, y, x+20, y-size*4/5);
		g.drawLine(x-6, y, x-30, y-size*7/10);
		g.drawLine(x+6, y, x+30, y-size*7/10);
		g.drawLine(x-8, y, x-40, y-size*4/7);
		g.drawLine(x+8, y, x+40, y-size*4/7);
		g2.setStroke(new BasicStroke(1));
		
		
	}
	
	public String getType() {
		return "Immobile";
	}

	public void setSize(int size){this.size=size;}
	
	public void addFrame(AquaFrame frame) {
		this.My_Frame=frame;

		x=(int)(Math.random()*10000)%(My_Frame.getWidth());
		
	}
	
	public int getLoc_x(){return x;}
	public int getLoc_y(){return y;}
	public void setLoc_x(int x) {
		this.x=x;
		
	}


	public void setLoc_y(int y) {
		this.y=y;
		
	}


	
	public int getSize() {

		return size;
	}


	@Override
	public String getData() {
		
		return name+"-Size:"+getSize()+", Color: green .";
	}
	
}
