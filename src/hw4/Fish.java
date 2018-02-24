package hw4;

/**
 * @author      YULIA
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Fish extends Swimmable implements MarineAnimal,Observable {
	/**
	 * this class creates a fish 
	 */

	//private String[] colors={"yellow","blue","green","red","orange","pink","cyan"};
	private Color col;
	private int size;
	private int x_speed,y_speed;
	private int x_front,y_front;
	private int x_dir=1,y_dir=1;
	private AquaFrame MyFrame;
	private int foodcounter;
	private boolean isSuspended=false;
	private CyclicBarrier barrier ;
	private double v_old,v_hor_new,v_ver_new;
	private int Frequency,counter=1;
	private HungerState state;


	public Fish(){

		new Satiated().SetState(this);
		

	}
	public Fish(int sizeoffish,int xspeed,int yspeed,Color color,AquaFrame frame, int Frequency){
		/**
		 * this constructor builds a new fish with its chosen fields
		 */
		super(xspeed,yspeed);	
		col=color;
		this.Frequency=Frequency;
		new Satiated().SetState(this);
		size=sizeoffish;	
		MyFrame=frame;
		x_speed=xspeed;
		y_speed=yspeed;
		x_front=size+10;
		y_front=size+10;
		

	}

	public void run(){
		/**
		 * this function runs the fish 
		 */
		while(true){
			try {
				sleep(200);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
			// sleep mode
			if(isSuspended==true)
				synchronized (this) {
					try {
						wait(); 
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			//movement
			if(isSuspended==false)
			{
				if(state.toString() == "Satiated"&&counter%Frequency==0&&(!Singleton.getInstance().getFood())){
					new Hungry().SetState(this);	
					notifyObservers();

				}
				else 
					counter++;

				//checking food


				if(Singleton.getInstance().getFood() && state.toString() == "Hungry") //, set movement to the center
				{
					if(barrier != null){
						try {
							barrier.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BrokenBarrierException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						//new speed:
						v_old = Math.sqrt((double)(x_speed*x_speed + y_speed*y_speed));  							
						double K = Math.abs((double)( (double)(y_front - MyFrame.getHeight()/2) / (double)(x_front - MyFrame.getWidth()/2)));
						v_hor_new = (double)(v_old / Math.sqrt((double)(K*K+1)));					
						v_ver_new = v_hor_new * K;							

						while(Singleton.getInstance().getFood())
						{
							try {
								sleep(200);
							} catch (InterruptedException e) {				
								e.printStackTrace();
							}

							if(x_front>MyFrame.getWidth()/2)
								x_dir = -1;
							else
								x_dir=1;
							if(y_front>MyFrame.getHeight()/2)
								y_dir=-1;
							else
								y_dir=1;

							x_front+=(int)v_hor_new*x_dir;
							y_front+=(int)v_ver_new*y_dir;					

							//if this animal eats the worm
							if((Math.abs(x_front-MyFrame.getWidth()/2) <= 5) && (Math.abs(y_front-MyFrame.getHeight()/2) <= 5))
							{
								this.eatInc();
								Singleton.getInstance().eatFood();
								new Satiated().SetState(this);
							}
							MyFrame.repaint();
						}

					}
					else //no food
					{
						x_front+=x_speed*x_dir;
						y_front+=y_speed*y_dir;
						MyFrame.repaint();

					}



				}

				else //no food
				{
					x_front+=x_speed*x_dir;
					y_front+=y_speed*y_dir;
					MyFrame.repaint();

				}

			}


			//check if direction changed

			if(x_front>=MyFrame.getContentPane().getWidth())
			{
				x_dir=-1;

			}
			if(y_front>=MyFrame.getContentPane().getHeight())
			{
				y_dir=-1;

			}
			if(x_front<=0)
			{
				x_dir=1;

			}

			if(y_front<=0)
			{
				y_dir=1;

			}

			MyFrame.repaint();

		}


	}

	public String getAnimalName() {

		return "Fish";
	}


	public void drawAnimal(Graphics g) {

		g.setColor(col);
		if(x_dir==1) // fish swims to right side
		{
			// Body of fish
			g.fillOval(x_front - size, y_front - size/4, size, size/2);
			// Tail of fish
			int[] x_t={x_front-size-size/4,x_front-size-size/4,x_front-size};
			int [] y_t = {y_front - size/4, y_front + size/4, y_front};
			Polygon t = new Polygon(x_t,y_t,3);
			g.fillPolygon(t);
			// Eye of fish
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(255-col.getRed(),255-col.getGreen(),255-col.getBlue()));
			g2.fillOval(x_front-size/5, y_front-size/10, size/10, size/10);
			// Mouth of fish
			if(size>70)
				g2.setStroke(new BasicStroke(3));
			else if(size>30)
				g2.setStroke(new BasicStroke(2));
			else
				g2.setStroke(new BasicStroke(1));
			g2.drawLine(x_front, y_front, x_front-size/10, y_front+size/10);
			g2.setStroke(new BasicStroke(1));
		}
		else // fish swims to left side
		{
			// Body of fish
			g.fillOval(x_front, y_front - size/4, size, size/2);
			// Tail of fish
			int[] x_t={x_front+size+size/4,x_front+size+size/4,x_front+size};
			int [] y_t = {y_front - size/4, y_front + size/4, y_front};
			Polygon t = new Polygon(x_t,y_t,3);
			g.fillPolygon(t);

			// Eye of fish
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(255-col.getRed(),255-col.getGreen(),255-col.getBlue()));
			g2.fillOval(x_front+size/10, y_front-size/10, size/10, size/10);
			// Mouth of fish
			if(size>70)
				g2.setStroke(new BasicStroke(3));
			else if(size>30)
				g2.setStroke(new BasicStroke(2));
			else
				g2.setStroke(new BasicStroke(1));
			g2.drawLine(x_front, y_front, x_front+size/10, y_front+size/10);
			g2.setStroke(new BasicStroke(1));
		}


	}


	public void setSuspend() {
		isSuspended=true;
	}


	public void setResume(){
		isSuspended=false;
		synchronized (this) {
			notify();

		}
	}


	public void setBarrier(CyclicBarrier b) {
		barrier=b;

	}


	public int getSize() {	
		return size;
	}

	public void setSize(int size){this.size=size;}

	public void eatInc() {
		foodcounter++;

	}


	public int getEatCount() {
		return foodcounter;
	}




	public void drawCreature(Graphics g) {
		drawAnimal(g);

	}

	public String getType() {
		return "Swimmable";

	}

	public void PaintFish(Color col) {
		this.col=col;

	}


	public void setSpeed(int x,int y){
		super.setHorSpeed(x);
		super.setVerSpeed(y);
		x_speed=x;
		y_speed=y;
	}

	public void addFrame(AquaFrame frame) {
		this.MyFrame=frame;

	}

	public String getData() {

		return getAnimalName()+"-Size:"+getSize()+", Color:"+getColor()+" Horspeed:"+getHorSpeed()+", Verspeed:"+getVerSpeed()+".";
	}

	public void setFeedFrequency(int num) {
		Frequency=num;

	}

	public void setState(HungerState state) {
		this.state=state;

	}

	public String getStateName() {

		return state.toString();
	}

	public Swimmable Clone() {
		
		return new Fish (size,x_speed,y_speed,col,MyFrame,Frequency);
	}
	
	public int getFoodFrequency() {

		return Frequency;
	}
	
	public String getColorRGB() {
		 return "("+col.getRed()+","+col.getGreen()+","+col.getBlue()+")";
		
	}
	
	public Color getColor() {
		
		return col;
	}
	@Override
	public void notifyObservers() {
		MyFrame.notify(getName()+": Im hungry");
		
	}
	public int getLoc_x(){return x_front;}
	public int getLoc_y(){return y_front;}

	public void setLoc_x(int x) {
		x_front=x;
		
	}

	public void setLoc_y(int y) {
		y_front=y;
		
	}

	public void setfoodcount(int num) {
		foodcounter=num;
		
	}



}
