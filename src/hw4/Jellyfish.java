package hw4;

/**
 * @author      YULIA 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Jellyfish extends Swimmable implements MarineAnimal,Observable {
	/**
	 * this class creats a jellyfish 
	 */

	//private String[] colors={"yellow","blue","green","red","orange","pink","cyan"};
	private Color col;
	private int size;
	private int x_front,y_front;
	private int x_speed,y_speed;
	private int foodcounter;
	private AquaFrame MyFrame;	
	private int x_dir=1,y_dir=1;
	private boolean isSuspended=false;
	private CyclicBarrier barrier ;
	private double v_old,v_hor_new,v_ver_new;
	private int Frequency,counter=1;
	private HungerState state;

	public Jellyfish(){

		new Satiated().SetState(this);

	}
	public Jellyfish (int sizeoffish,int xspeed,int yspeed,Color color,AquaFrame frame,int Frequency){
		/**
		 *this constructor builds a new jellyfish with its chosen fields
		 */
		super(xspeed,yspeed);	
		col=color;
		size=sizeoffish;	
		new Satiated().SetState(this);
		this.Frequency=Frequency;
		MyFrame=frame;
		x_speed=xspeed;
		y_speed=yspeed;
		x_front=size;
		y_front=size;
	}



	public void run(){
		/**
		 *this function runs the jellyfish according to the chosen movement speed, sleep time etc
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
					try {
						barrier.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					v_old = Math.sqrt(x_speed*x_speed + y_speed*y_speed);  							
					double K = Math.abs( (double)(y_front - MyFrame.getHeight()/2) / (double)(x_front - MyFrame.getWidth()/2));
					v_hor_new = v_old / Math.sqrt(K*K+1);					
					v_ver_new = v_hor_new * K;											


					while(Singleton.getInstance().getFood()==true)
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

			//check if direction changed

			if(x_front>=MyFrame.getWidth())
			{
				x_dir=-1;

			}
			if(y_front>=MyFrame.getHeight())
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

		return "Jellyfish ";
	}

	public void drawAnimal(Graphics g) {

		int numLegs;
		if(size<40)
			numLegs = 5;
		else if(size<80)
			numLegs = 9;
		else
			numLegs = 12;
		g.setColor(col);
		g.fillArc(x_front - size/2, y_front - size/4, size, size/2, 0, 180);
		for(int i=0; i<numLegs; i++)
			g.drawLine(x_front - size/2 + size/numLegs + size*i/(numLegs+1),y_front, x_front - size/2 + size/numLegs + size*i/(numLegs+1),y_front+size/3);

	}


	public void setSuspend() {
		isSuspended=true;
	}


	public void setResume() {
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

	public void setSize(int size){this.size=size;}


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



	public void setState(HungerState state) {
		this.state=state;

	}

	public void setFeedFrequency(int num) {
		Frequency=num;

	}

	public String getStateName() {

		return state.toString();
	}
	@Override
	public Swimmable Clone() {
		return new Jellyfish (size,x_speed,y_speed,col,MyFrame,Frequency);

	}

	public int getFoodFrequency() {

		return Frequency;
	}

	public String getColorRGB() {
		return "("+col.getRed()+","+col.getGreen()+","+col.getBlue()+")";

	}
	@Override
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
