package hw4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPanelDecorator extends JPanel implements ActionListener{


	private static final long serialVersionUID = 1L;
	private HashSet<Swimmable> My_Animals;;
	private AquaPanel My_Panel;	
	private JComboBox<String> AnimalDetails;
	private JButton ChangeColor;
	private JLabel Label;

	


	public JPanelDecorator(AquaPanel Panel) {
		super(new BorderLayout());
		
		My_Panel=Panel;
		My_Animals=My_Panel.GetAnimals();
		
		this.setSize(500, 150);
		this.setBackground(Color.WHITE);	
		
		
		String [] T=new String[My_Panel.getAnimalCount()];
		int i=0;

		String temp;
		for(Swimmable s:My_Animals)
		{
			temp=s.getData();			
			T[i]=temp;
			i++;
			
		}
		
		Label=new JLabel("Please choose an animal then press 'Change color' button");
		Label.setBounds(10, 15, 450, 20);
		AnimalDetails=new JComboBox<String>(T);		
		AnimalDetails.setBounds(10, 50, 450, 20);
		ChangeColor=new JButton("Change Color");
		ChangeColor.setBounds(10, 85, 120, 20);
		
		
		ChangeColor.addActionListener(this);
	
		
		this.add(Label);
		this.add(AnimalDetails);
		this.add(ChangeColor);
		My_Panel.add(this);
	
		
	}

	@Override
	public void actionPerformed(ActionEvent arg) {
		if(arg.getSource()==ChangeColor)
		{
			Color NewColor = JColorChooser.showDialog(this,"Choose fish color", Color.white);
			
            if(NewColor != null){       
                int i=0;
                for(Swimmable s:My_Animals)
                {
                	if(i==AnimalDetails.getSelectedIndex())
                	{
                		((MarineAnimal)s).PaintFish(NewColor);
                		
                	}
                	i++;
                }
             }
			this.setVisible(false);

		}
		
		
	}



}
