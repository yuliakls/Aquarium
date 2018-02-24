package hw4;

/**
 * @author      YULIA
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class AddCreatureDialog extends JDialog implements ActionListener,ChangeListener {
	/**
	 * This class creates a new animal 
	 */

	private static final long serialVersionUID = 1L;

	private AquaFrame MyFrame;
	//My fields to choose an animal
	private String[] animals={"Fish","Jellyfish"};
	private String[] plants={"Zostera","Laminaria"};
	private String[] colors={"yellow","blue","green","red","orange","pink","cyan"};
	private JComboBox<String> Type,color;
	private JSlider Size,XSpeed,YSpeed,Foodneed;
	private JLabel Sizelabel,xlabel,ylabel,foodlabel;
	private boolean animal;

	private JButton Finish;



	public AddCreatureDialog(AquaFrame Frame,String title,boolean animal){

		super(Frame,title,true); //locks the dialog window
		MyFrame=Frame;
		this.animal=animal;
		this.setSize(400,400);
		setLayout(null);


		Sizelabel=new JLabel("Size: "+(320-20)/2+" px");
		Sizelabel.setBounds(10,95, 120, 20);			
		Size=new JSlider(20,320);
		Size.setBounds(10, 115, 100, 20);
		Size.addChangeListener(this);

		XSpeed=new JSlider(1,10);
		YSpeed=new JSlider(1,10);
		Foodneed=new JSlider(100,500);
		if(animal){
			Type=new JComboBox<String>(animals);
			Type.setBounds(10, 15, 100, 20);

			color=new JComboBox<String>(colors);
			color.setBounds(10,55,100,20);


			xlabel=new JLabel("Horizontal speed: 5");
			xlabel.setBounds(10,155, 120, 20);

			XSpeed.setBounds(10, 175, 100, 20);
			XSpeed.addChangeListener(this);


			ylabel=new JLabel("Vertical speed: 5");
			ylabel.setBounds(10,215, 120, 20);

			YSpeed.setBounds(10, 235, 100, 20);
			YSpeed.addChangeListener(this);
			
			foodlabel=new JLabel("Feeding frequency: 300");
			foodlabel.setBounds(10,275, 160, 20);
			
			Foodneed.setBounds(10,295 , 100, 20);
			Foodneed.addChangeListener(this);
			
			this.getContentPane().add(color);
			this.getContentPane().add(xlabel);
			this.getContentPane().add(ylabel);
			this.getContentPane().add(XSpeed);
			this.getContentPane().add(YSpeed);
			this.getContentPane().add(foodlabel);
			this.getContentPane().add(Foodneed);
		}
		else{

			Type=new JComboBox<String>(plants);
			Type.setBounds(10, 15, 100, 20);

		}

		Finish=new JButton("Add");
		Finish.setBounds(240, 320, 120, 20);	
		Finish.addActionListener(this);

		this.getContentPane().add(Finish);
		this.getContentPane().add(Type);
		this.getContentPane().add(Size);
		this.getContentPane().add(Sizelabel);

	}


	public void actionPerformed(ActionEvent e) {


		if(e.getSource()==Finish)
		{
			if(animal){ // if the user choose an animal
				if(MyFrame.getAnimalCount()<5){

					Field field;
					Color c=Color.black;
					try {
						field = Color.class.getField(color.getSelectedItem().toString());
						c = (Color)field.get(null);
					} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					
					SeaCreature temp= new AnimalFactory().produceSeaCreature(Type.getSelectedItem().toString()); //////***
					temp.setSize(Size.getValue());///****SEACREATURE
					((Swimmable)temp).setSpeed(XSpeed.getValue(), YSpeed.getValue());
					((Swimmable)temp).addFrame(MyFrame);
					((Swimmable)temp).setFeedFrequency(Foodneed.getValue());
					((MarineAnimal)temp).PaintFish(c);
					MyFrame.Add_Creature(temp);	
					((Swimmable)temp).start();


				}
				else
					System.out.println("Maximun amount of animals");
				this.setVisible(false);
				
			}

			// if user choose plant
			else{ 
				if(MyFrame.getPlantCount()<5){

					SeaCreature temp= new PlantFactory().produceSeaCreature(Type.getSelectedItem().toString());
					//adding parameters
					
					temp.setSize(Size.getValue());
					((Immobile)temp).addFrame(MyFrame);
					
					MyFrame.Add_Creature(temp);
					MyFrame.repaint();
				}
				else
					System.out.println("Maximun amount of plants");
			}
		}


	}



	public void stateChanged(ChangeEvent e) {
		if(e.getSource()==Size)
			Sizelabel.setText("Size: "+Size.getValue()+" px");
		else if(e.getSource()==XSpeed)
			xlabel.setText("Horizontal speed: "+XSpeed.getValue());
		else if(e.getSource()==YSpeed)
			ylabel.setText("Vertical speed: "+YSpeed.getValue());
		else if(e.getSource()==Foodneed)
			foodlabel.setText("Feeding frequency: "+Foodneed.getValue());

	}





}
