	package hw4;

/**
 * @author      YULIA
 */


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.CyclicBarrier;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;





public class AquaPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private boolean infobox=true;
	private AquaFrame MyFrame;	
	private BufferedImage img = null;
	private JButton Add_Animal,Add_Plant,Sleep,Wake_up,Reset,Food,Info,Exit,Duplicate,Decorator;
	private JPanel myPanel;
	private HashSet<SeaCreature> My_Creatures=new HashSet<SeaCreature>();

	private String [] columnNames={"Animal","Color","Size","Hor.speed", "Ver.speed","Eat counter"};
	private Object [][] data=new Object[6][6];
	private JTable infoTable;
	//private boolean isFood=false;
	private CyclicBarrier barrier;




	public AquaPanel(AquaFrame Frame){
		MyFrame=Frame;

		BorderLayout myBorderLayout = new BorderLayout();

		this.setLayout(myBorderLayout);


		//buttons creation

		Add_Animal=new JButton("Add animal");
		Add_Plant=new JButton("Add plant");
		Sleep =new JButton("Sleep");
		Wake_up=new JButton("Wake up");
		Reset=new JButton("Reset");
		Food =new JButton("Food");
		Info=new JButton("Info");
		Duplicate= new JButton("Duplicate Animal");
		Exit=new JButton("Exit");
		Decorator=new JButton("Decorator");


		//color changing

		Add_Animal.setBackground(Color.LIGHT_GRAY);
		Add_Plant.setBackground(Color.LIGHT_GRAY);
		Sleep.setBackground(Color.LIGHT_GRAY);
		Wake_up.setBackground(Color.LIGHT_GRAY);
		Reset.setBackground(Color.LIGHT_GRAY);
		Food.setBackground(Color.LIGHT_GRAY);
		Info.setBackground(Color.LIGHT_GRAY);
		Duplicate.setBackground(Color.LIGHT_GRAY);
		Exit.setBackground(Color.LIGHT_GRAY);
		Decorator.setBackground(Color.LIGHT_GRAY);

		myPanel = new JPanel();

		myPanel.setLayout(new GridLayout(2,9));//rows,colomns

		myPanel.add(Add_Animal);
		myPanel.add(Add_Plant);
		myPanel.add(Sleep);
		myPanel.add(Wake_up);
		myPanel.add(Reset);
		myPanel.add(Food);
		myPanel.add(Info);
		myPanel.add(Duplicate);
		myPanel.add(Decorator);
		myPanel.add(Exit);


		Add_Animal.addActionListener(this);
		Add_Plant.addActionListener(this);
		Sleep.addActionListener(this);
		Wake_up.addActionListener(this);
		Reset.addActionListener(this);
		Food.addActionListener(this);
		Info.addActionListener(this);
		Duplicate.addActionListener(this);
		Exit.addActionListener(this);
		Decorator.addActionListener(this);
		this.add(myPanel,BorderLayout.SOUTH);


	}


	//==================== BACKGROUND =====================

	public void setBackgr(int background){
		/**
		 *this function changes the background of the frame to blue, image or none
		 */
		if(background==0){ //none
			img=null;
			this.setBackground(new Color(0,0,0,0));
		} 
		else if(background==1){//blue
			img=null;
			this.setBackground(Color.BLUE);

		} 
		else if(background==2){//picture

			this.repaint();
			try{ 
				
				img = ImageIO.read(new File("src/hw4/aquarium_background.jpg"));
			} 
			catch (IOException e) {
				System.out.println("Cannot load image");   
			}
		} 

		else{
			System.out.println("Iligal value"); 
		}		
	}

	public void paintComponent(Graphics g){
		/**
		 *this function draws the images
		 */
		super.paintComponent(g); 
		if(img!=null) {
			g.drawImage(img, 0,0, getWidth(), getHeight(), this);
		}

		for (SeaCreature s : My_Creatures){
			s.drawCreature(g);
		}


		if(Singleton.getInstance().getFood()==true)
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.red);
			g2.drawArc(getWidth()/2, getHeight()/2-5, 10, 10, 30, 210);	   
			g2.drawArc(getWidth()/2, getHeight()/2+5, 10, 10, 180, 270);	
			g2.setStroke(new BasicStroke(1));
		}
	}

	public void actionPerformed(ActionEvent arg)
	{
		/**
		 *this function starts an action according to the users choice from the buttons
		 *add animal, sleep, wake up, reset, food, info or exit
		 */
		if(arg.getSource()==Add_Animal)
		{
			AddCreatureDialog dial=new AddCreatureDialog(MyFrame,"Add an animal to aquarium",true); //animal should be painted
			dial.setVisible(true);
		}

		else if(arg.getSource()==Add_Plant)
		{
			AddCreatureDialog dial=new AddCreatureDialog(MyFrame,"Add an animal to aquarium",false);//plant is green by default so it should not be painted
			dial.setVisible(true);
			this.repaint();
		}


		else if(arg.getSource()==Sleep)
		{
			for(SeaCreature s: My_Creatures) //check iterator option !!
				if(s.getType()=="Swimmable")
					((Swimmable)s).setSuspend();
		}

		else if(arg.getSource()==Wake_up)
		{
			for(SeaCreature s: My_Creatures) //check iterator option !!
				if(s.getType()=="Swimmable")
					((Swimmable)s).setResume();	
		}

		else if(arg.getSource()==Reset)
		{
			My_Creatures.clear();
			MyFrame.repaint();

		}

		else if(arg.getSource()==Food)
		{

			if(getAnimalCount()>0){
				if(Singleton.getInstance().getFood()==false){

					int counter=0;
					for(SeaCreature s: My_Creatures) //check iterator option !!
					{
						if(s.getType()=="Swimmable")
							if(((Swimmable)s).getStateName()=="Hungry")
								counter++;
					}
					if(counter>0)
					{	
						Singleton.getInstance().setFood();
						barrier = new CyclicBarrier(counter);
						for(SeaCreature s: My_Creatures) //check iterator option !!
						{
							if(s.getType()=="Swimmable")
								if(((Swimmable)s).getStateName()=="Hungry")
									((Swimmable)s).setBarrier(barrier);
						}
					}
					else
						System.out.println("No hungry animals");

				}
				else
				{

					System.out.println("Only one worm at a time");
				}
			}
			else
			{

				System.out.println("No animals in the Aquarium");
			}

		}

		else if(arg.getSource()==Info) //FIX THIS
		{
			/**
			 * draws a jtable according to the animals data
			 */
			if(infobox){
				infobox=false;
				int sum=0;
				int i=0;

				for(SeaCreature s:My_Creatures){ //for each animal in my_animals
					if(s.getType()=="Swimmable"){
						data[i][0]=((Swimmable)s).getAnimalName();
						data[i][1]=((Swimmable)s).getColor();
						data[i][2]=((Swimmable)s).getSize();
						data[i][3]=((Swimmable)s).getHorSpeed();
						data[i][4]=((Swimmable)s).getVerSpeed();
						data[i][5]=((Swimmable)s).getEatCount();
						sum+=((Swimmable)s).getEatCount();
						i++;
					}
				}
				data[5][0]="Total";
				data[5][5]=sum;
				infoTable=new JTable(data,columnNames); //jtable
				//it=new JScrollPane(infoTable);
				add(infoTable);
				validate();
			}


			else{
				/**
				 *if the user pressed an even number of times on the button 
				 */
				infobox=true;
				remove(infoTable);
				MyFrame.repaint();
			}
		}


		else if(arg.getSource()==Duplicate){
			if (getAnimalCount()!=0){
				if(getAnimalCount()<5){
					Proto p=new Proto(this);
					p.setVisible(true);
				}
				else
					System.out.println("Aquarium is full");
			}
			else
				System.out.println("Who do you wanna duplicate? No animals in the aquarium");
		}

		else if(arg.getSource()==Decorator){
			if(getAnimalCount()!=0){
				JPanelDecorator dial=new JPanelDecorator(this);
				this.add(dial);
				this.repaint();
			}
			else
				System.out.println("Who do you wanna decorate? No animals in the aquarium");

		}

		else if(arg.getSource()==Exit) 
		{
			System.exit(0);
		}
	}

	public void Add_Creature(SeaCreature seacreature){
		/**
		 * this function adds the creature
		 */

		My_Creatures.add(seacreature);

	}

	public int getAnimalCount(){
		/**
		 * this function returns the number of animals that has been added
		 */

		int  animals=0;
		//count animals
		for(SeaCreature s: My_Creatures) //check iterator option !!
		{
			if(s.getType()=="Swimmable")
				animals++;
		}

		return animals;
	}

	public int getPlantCount(){
		/**
		 * this function returns the number of animals that has been added
		 */

		int  plants=0;
		//count animals
		for(SeaCreature s: My_Creatures) //check iterator option !!
		{
			if(s.getType()=="Immobile")
				plants++;
		}

		return plants;
	}

	public HashSet<Swimmable> GetAnimals(){

		HashSet<Swimmable> temp=new HashSet<Swimmable>();

		for(SeaCreature s: My_Creatures) //check iterator option !!
		{
			if(s.getType()=="Swimmable")
				temp.add((Swimmable)s);
		}
		return temp;
	} 
	

	public HashSet<SeaCreature> GetCreatures(){

		
		
		return My_Creatures;
	} 
	

	public AquaFrame getFrame(){return MyFrame;}


	




}





