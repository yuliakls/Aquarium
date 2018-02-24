package hw4;

/**
 * @author      YULIA
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class AquaFrame extends JFrame implements ActionListener,Observer
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Memento> statesList = new ArrayList<Memento>();

	private AquaPanel panel;
	private String[] names = {"Exit","Image","Blue","None","Help"};
	private JMenu m1, m2, m3;
	private JMenuItem[] mi;
	private JMenuBar mb;
	private HashSet<SeaCreature> My_Creatures;
	private JDialog Window;

	private JComboBox<String> AnimalDetails;
	private JButton Save,Restore;
	private JLabel Label;

	public static void main(String[]args)
	{
		/**
		 * the main creates a new frame 
		 */
		AquaFrame aqua = new AquaFrame();
		aqua.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		aqua.setSize(800,600);
		aqua.setVisible(true);
	}

	public AquaFrame()
	{	/**
	 * this constructor creates a new panel with a menu 
	 * file, background, help
	 */
		super("my Aquarium");
		panel = new AquaPanel(this);
		add(panel);
		panel.setVisible(true);

		mb = new JMenuBar();
		m1 = new JMenu("File");
		m2 = new JMenu("Background");
		m3 = new JMenu("Help");
		mi = new JMenuItem[names.length];

		for(int i=0;i<names.length;i++)
		{
			mi[i]=new JMenuItem(names[i]);
			mi[i].addActionListener(this);
		}

		/**
		 * mi=menu item 
		 * mb=menu bar
		 * m1,m2,m3=jmenu  for file, background and help
		 */ 

		m1.add(mi[0]); //file
		m2.add(mi[1]); //backgrould option 1
		m2.addSeparator();
		m2.add(mi[2]); //option 2
		m2.addSeparator();
		m2.add(mi[3]);//option 3
		m3.add(mi[4]); //help
//		m4.add(mi[6]); //memento
//		m4.addSeparator();
//		m4.add(mi[7]);

		mb.add(m1);
		mb.add(m2);
		mb.add(m3);
//		mb.add(m4);
		setJMenuBar(mb);

		My_Creatures=panel.GetCreatures();
	}

	public void actionPerformed(ActionEvent e) {
		/**
		 *this function connects between the button and an action
		 */
		if(e.getSource() == mi[0])
			destroy();
		else if(e.getSource() == mi[1])
			panel.setBackgr(2);
		else if(e.getSource() == mi[2])
			panel.setBackgr(1);
		else if(e.getSource() == mi[3])
			panel.setBackgr(0);
//		else if(e.getSource() == mi[4])
//			printHelp();
//		else if (e.getSource()==mi[6])//save
//		{
//			Window=new JDialog();
//			Window.setSize(400,400);
//			animalchoosescreen();
//			Save=new JButton("Save");
//			Save.setBounds(10, 85, 120, 20);
//			Save.addActionListener(this);
//			Window.add(Save);
//			Window.setVisible(true);			
//			
//
//		}
//		else if (e.getSource()==mi[7])//restore
//		{
//
//			Window=new JDialog();
//			Window.setSize(400,400);
//			animalchoosescreen();
//			Restore=new JButton("Restore");
//			Restore.setBounds(10, 85, 120, 20);
//			Restore.addActionListener(this);
//			Window.add(Restore);
//			Window.setVisible(true);			
//			
//		}
/*		else if (e.getSource()==Save)
		{
			int i=0;
			for(SeaCreature s:My_Creatures)
			{
				if(i==AnimalDetails.getSelectedIndex())
				{
					Originator temp=new Originator();
					temp.setState(s);
					statesList.add(temp.createMemento());
				}
				i++;
			}
		}
		else if (e.getSource()==Restore)
		{
			int i=0;
			for(SeaCreature s:My_Creatures)
			{
				if(i==AnimalDetails.getSelectedIndex())
				{
					Originator temp=new Originator();
					temp.setState(s);					
					temp.setMemento(temp.createMemento());					
				}
				i++;
			}

		}*/




	}

	public void destroy() {
		/**
		 *this function closes the frame
		 */
		System.exit(0);
	}

	public void printHelp() {
		/**
		 *this function prints a messege in a new dialog box
		 */
		JOptionPane.showMessageDialog(this, "Home Work 3\nGUI @ Threads");
	}

	public void Add_Creature(SeaCreature seacreature){
		/**
		 * this function adds a new animal
		 */
		panel.Add_Creature(seacreature);
	}

	public int getAnimalCount(){
		/**
		 *returns the num of animals
		 */
		return panel.getAnimalCount();
	}

	public int getPlantCount(){
		/**
		 *returns the num of plants
		 */
		return panel.getPlantCount();
	}


	public void notify(String msg) {
		JOptionPane.showMessageDialog(null, msg, "InfoBox: " + "Food request", JOptionPane.INFORMATION_MESSAGE);

	}


	public void animalchoosescreen(){


		String [] T=new String[panel.getAnimalCount()];
		int i=0;

		String temp;

		for(SeaCreature s:My_Creatures)
		{
			temp=s.getData();			
			T[i]=temp;
			i++;

		}

		Label=new JLabel("Please choose an animal then press 'Change color' button");
		Label.setBounds(10, 15, 450, 20);
		AnimalDetails=new JComboBox<String>(T);		
		AnimalDetails.setBounds(10, 50, 450, 20);
		Window.add(Label);
		Window.add(AnimalDetails);		


	}


}
