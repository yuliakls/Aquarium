package hw4;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Proto extends JDialog implements ActionListener,ChangeListener{

	private static final long serialVersionUID = 1L;
	private AquaPanel My_Panel;
	private HashSet<Swimmable> My_Animals;
	private JLabel label1,Sizelabel,xlabel,ylabel,foodlabel;
	private JComboBox<String> AnimalDetails,color;
	private JButton Finish, Add;
	private JSlider Size,XSpeed,YSpeed,Foodneed;
	private Swimmable temp=null;	
	private String[] colors={"yellow","blue","green","red","orange","pink","cyan"};


	public Proto(AquaPanel Panel){
		super();

		My_Panel=Panel;
		label1=new JLabel("Which animal would you like to duplicate?");
		label1.setBounds(0, 15, 400, 30);
		this.getContentPane().add(label1);
		this.setSize(400,400);
		setLayout(null);
		My_Animals=My_Panel.GetAnimals();

		String [] T=new String[My_Panel.getAnimalCount()];
		int i=0;
		String temp;
		for(Swimmable s:My_Animals)
		{
			temp=s.getData();
			T[i]=temp;
			i++;	
		}
		AnimalDetails=new JComboBox<String>(T);
		AnimalDetails.setBounds(0, 45, 400, 20);
		this.getContentPane().add(AnimalDetails);
		this.setSize(500,400);
		Finish=new JButton("Duplicate");
		Finish.setBounds(240, 320, 120, 20);	
		Finish.addActionListener(this);
		this.getContentPane().add(Finish);

	}


	public void actionPerformed(ActionEvent e) { 
		if(e.getSource()==Finish)
		{				
			
			int i=0;
			for(Swimmable s:My_Animals)
			{
				if(i==AnimalDetails.getSelectedIndex())
				{
					temp=s.Clone();
					Finish.setVisible(false);
					changeData();
					
				}
				i++;
			}	
			
		}
		if(e.getSource()==Add)
		{	
			
			
			Field field;
			Color c=Color.black;
			try {
				field = Color.class.getField(color.getSelectedItem().toString());
				c = (Color)field.get(null);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e1) {
				c=temp.getColor();
			}
			temp.setSize(Size.getValue());
			((Swimmable)temp).setSpeed(XSpeed.getValue(), YSpeed.getValue());
			((Swimmable)temp).addFrame(My_Panel.getFrame());
			((Swimmable)temp).setFeedFrequency(Foodneed.getValue());
			((MarineAnimal)temp).PaintFish(c);
			My_Panel.getFrame().Add_Creature(temp);	
			((Swimmable)temp).start();
			
			this.setVisible(false);
		}


	}
	
	
	private void changeData(){
		
			

		Sizelabel=new JLabel("Size: "+(320-20)/2+" px");
		Sizelabel.setBounds(10,135, 120, 20);			
		Size=new JSlider(20,320);
		Size.setBounds(10, 155, 100, 20);
		Size.addChangeListener(this);
		
		
		XSpeed=new JSlider(1,10,temp.getHorSpeed());
		YSpeed=new JSlider(1,10,temp.getVerSpeed());
		Foodneed=new JSlider(100,500,temp.getFoodFrequency());
		Add=new JButton("Change");
		Add.setBounds(240, 320, 120, 20);	
		Add.addActionListener(this);
		
		color=new JComboBox<String>(colors);
		color.setBounds(10,95,100,20);
		color.addItem(temp.getColorRGB());
		color.setSelectedIndex(colors.length);

		xlabel=new JLabel("Horizontal speed: "+temp.getHorSpeed());
		xlabel.setBounds(10,195, 120, 20);

		XSpeed.setBounds(10, 215, 100, 20);
		XSpeed.addChangeListener(this);


		ylabel=new JLabel("Vertical speed: "+temp.getVerSpeed());
		ylabel.setBounds(10,255, 120, 20);

		YSpeed.setBounds(10, 275, 100, 20);
		YSpeed.addChangeListener(this);
		
		foodlabel=new JLabel("Feeding frequency: "+temp.getFoodFrequency());
		foodlabel.setBounds(10,315, 160, 20);
		
		Foodneed.setBounds(10,335 , 100, 20);
		Foodneed.addChangeListener(this);
		
		this.getContentPane().add(color);
		this.getContentPane().add(xlabel);
		this.getContentPane().add(ylabel);
		this.getContentPane().add(XSpeed);
		this.getContentPane().add(YSpeed);
		this.getContentPane().add(foodlabel);
		this.getContentPane().add(Foodneed);
		this.getContentPane().add(Add);
		this.getContentPane().add(Size);
		this.getContentPane().add(Sizelabel);
		
			
		
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


