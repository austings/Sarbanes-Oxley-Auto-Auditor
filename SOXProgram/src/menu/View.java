package menu;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;

import menu.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


/**
 * author- Austin Sierra
 * last edit- Nov 12, 2017
 * 
 * View- The main view is a GUI for the SOX Audit Processor. By using swing elements and a CardLayout, the GUI
 * guides the user through two windows of required inputs before the actual audit is run.
 * 
 */
public class View extends JFrame{
	
	//						//
	//		VARIABLES		//
	//						//
	//card 'deck'
	private JPanel cards = new JPanel();
	
	//first page. first 'card' in the deck 
	private JPanel card1 = new JPanel();
	private JPanel jp1_1 = new JPanel();	//center panel first page
	private JPanel jp1_2 = new JPanel();	//north panel first page
	private JPanel jp1_3 = new JPanel();	//south panel first page
	
	//components on first page
	private JComboBox jcb1;
	private JButton back1;
	private JButton next1;
	
	//second page. second 'card' in the deck
	private JPanel card2 = new JPanel();
	private JPanel jp2_1 = new JPanel();
	private JPanel jp2_2 = new JPanel();;
	private JPanel jp2_3 = new JPanel();;
	
	//data for dynamic textfields based on audit choice
	private String[] auditChoices = {"TLO A PNG", "TLO B PNG", "TLO A ASO", "TLO B ASO", "TLO A PATH", "TLO B PATH", "STM A", "STM B", "ESS A"};
	private String jcbSelection = "";
	private ArrayList<JPanel> sourcePanels = new ArrayList<JPanel>();// <-- Each textfield, depending on the audit type selected, is stored in its own panel
	
	//buttons on second page
	private JButton back2;
	private JButton next2;
	
	//progress bar (NOT YET IMPLEMENTED)
	private final static int interval = 1000;
	private int i;
	private Timer t;
	private JProgressBar pgb;
	
	
	//						//
	//		METHODS			//
	//						//
	
	
	/**
	 * Constructor
	 * 
	 * The View constructor establishes the JFrame, and once completed begins construction of the first 'card'
	 */
	public View()
	{
		//window size, titles, location on screen etc.
		super();
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(400,300));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle("SOX Audit Processor - (C) Paychex 2017");
		this.pack();

		//set up layouts of panels
		//'deck'
		cards.setLayout(new CardLayout());
		//card1
		card1.setLayout(new BorderLayout());
		cards.add(card1, "1");
		//card2
		card2.setLayout(new BorderLayout());
		cards.add(card2, "2");
		
		//construct the first card
		firstMenu();
		
		//add the cards to the JFrame and make the window visible
		this.add(cards);
		this.setVisible(true);
	}
	
	/**
	 * firstMenu- the firstMenu method takes no parameters and returns no values.
	 * 
	 * The goal of this method is to add components to the first card, and detail the actions
	 * taken when the components are activated.
	 */
	public void firstMenu()
	{
		
		//Create the center panel, which holds the combo-box
		card1.add(jp1_1, BorderLayout.CENTER);
		jp1_1.setLayout(new FlowLayout());
		jcb1 = new JComboBox(auditChoices);
		jp1_1.add(jcb1);
		
		//Create the north title panel
		card1.add(jp1_2, BorderLayout.NORTH);
		jp1_2.setLayout(new BorderLayout(0,5));
		JLabel title = new JLabel("SOX Audit Processor");
		JLabel text = new JLabel("Select the audit to run.");
		title.setFont(new Font(title.getFont().getName(),Font.BOLD,22));
		title.setHorizontalAlignment(JLabel.CENTER);
		text.setHorizontalAlignment(JLabel.CENTER);
		jp1_2.add(title,BorderLayout.CENTER);
		jp1_2.add(text,BorderLayout.SOUTH);
		
		//Create the south panel, which holds the buttons.
		card1.add(jp1_3,BorderLayout.SOUTH);
		jp1_3.setLayout(new FlowLayout(FlowLayout.CENTER,100,20));
		//back1- since there is no previous menu, back1 quits the application.
		back1 = new JButton("Exit");
		back1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
			
		});
		//next1- next1 stores the current combo-box selection to so the application can determine what the second card should look like.
		next1 = new JButton("Next");
		next1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) 
			{
				((CardLayout) cards.getLayout()).show(cards, "2");
				jcbSelection = jcb1.getSelectedItem().toString();
				secondMenu();
			}
			
		});
		
		//add buttons to the south panel.
		jp1_3.add(back1);
		jp1_3.add(next1);
		
	}
	
	/**
	 * secondMenu- the secondMenu method takes no parameters and returns no values.
	 * 
	 * The goal of this method is to add components to the second card, and detail the actions
	 * taken when the components are activated.
	 * 
	 * This page contains text fields which is how the program collects the source file paths for compiling the output.
	 * It also contains the text field of the destination the output should be placed.
	 */
	public void secondMenu()
	{
		//Clear everything from this menu. The reason for doing this is that the user is able to
		//go back to the first page and change their combo-box selection. Because this page is
		//determined by that selection, we need to clear any components which might already exist here.
		card2.removeAll();
		jp2_2.removeAll();
		jp2_3.removeAll();
		jp2_1.removeAll();
		
		
		//Create the center panel, which holds all the textfields.
		card2.setLayout(new BorderLayout());
		
		ArrayList<JPanel> needed = new ArrayList<JPanel>();
		//determine number of files required for this particular audit and store in "needed"
		int numOfFiles = 0;
		int ID = 0;
		
		/*
		 * Loop through each one of the audit choices, and compare it to the one that was selected.
		 * Once we find it, do a switch statement based on the #ID of the audit.
		 * 1-TLO PNG A
		 * 2-TLO PNG B
		 * 3-TLO ASO A
		 * 4-TLO ASO B
		 * 5-TLO PATH A
		 * 6-TLO PATH B
		 * 7-STM A
		 * 8-STM B
		 * 9-ESS A
		 * 
		 * The switch statement pulls the panels from the master sourcePanels list and puts it into another
		 * list which will be printed to the screen. 
		 * EX: The TLO PNG A report requires 7 files, and there are 14 total files encompassing all the audits.
		 * So we take the ones we need: 1, 6, 7, 8 ,9, 13, & 14. The ids of each report are all listed below
		 * 
		 *  EOMClientBaseSTM = 0
		 *  EOMClientBaseTLO = 1
	 	 *  currentClientSTM = 2
	 	 *  currentClientTLO = 3
	 	 *  qryActive1152 = 4
	 	 *  qryActive1257 = 5
	 	 *  tblTLOwAS = 6
	 	 *  EDW = 7
	 	 *  PRPackage = 8
		 *  PRStatus = 9
		 *  qryCancelTLO = 10
		 *  qryCancelSTM = 11
		 *  activeSTMCodes = 12
		 *  cancelPayroll = 13
		 *  output = 14
		 */
		for(String s : auditChoices)
		{
			ID++;
			if(s.equals(jcbSelection))
			{
				sourceFiles();
				switch(ID)
				{
					case 1: 
						numOfFiles=7;//TLO PNG A (soxplan file +1 for output)
						needed.add(sourcePanels.get(1));//EOMClientBaseTLO
						needed.add(sourcePanels.get(6));//tblTLOwAS
						needed.add(sourcePanels.get(7));//EDW
						needed.add(sourcePanels.get(8));//PRPackage
						needed.add(sourcePanels.get(9));//PRStatus
						needed.add(sourcePanels.get(13));//cancelPayroll
						needed.add(sourcePanels.get(14));//output
						break;
					case 2: 
						numOfFiles=7;//TLO PNG B
						needed.add(sourcePanels.get(1));//EOMClientBaseTLO
						needed.add(sourcePanels.get(3));//currentClientTLO
						needed.add(sourcePanels.get(4));//qryActive1152
						needed.add(sourcePanels.get(7));//EDW
						needed.add(sourcePanels.get(8));//PRPackage
						needed.add(sourcePanels.get(10));//qryCancelTLO
						needed.add(sourcePanels.get(14));//output
						break;
					case 3:
						numOfFiles=7;//TLO ASO A
						needed.add(sourcePanels.get(1));//EOMClientBaseTLO
						needed.add(sourcePanels.get(6));//tblTLOwAS
						needed.add(sourcePanels.get(7));//EDW
						needed.add(sourcePanels.get(8));//PRPackage
						needed.add(sourcePanels.get(9));//PR Status
						needed.add(sourcePanels.get(13));//cancelPayroll
						needed.add(sourcePanels.get(14));//output
						break;
					case 4:
						numOfFiles=7;//TLO ASO B
						needed.add(sourcePanels.get(1));//EOMClientBaseTLO
						needed.add(sourcePanels.get(3));//currentClientTLO
						needed.add(sourcePanels.get(4));//qryActive1152
						needed.add(sourcePanels.get(7));//EDW
						needed.add(sourcePanels.get(8));//PRPackage
						needed.add(sourcePanels.get(10));//qryCancelTLO
						needed.add(sourcePanels.get(14));//output
						break;
					case 5:
						numOfFiles=7;//TLO PATH A
						needed.add(sourcePanels.get(1));//EOMClientBaseTLO
						needed.add(sourcePanels.get(6));//tblTLOwAS
						needed.add(sourcePanels.get(7));//EDW
						needed.add(sourcePanels.get(8));//PRPackage
						needed.add(sourcePanels.get(9));//PR Status
						needed.add(sourcePanels.get(13));//cancelPayroll
						needed.add(sourcePanels.get(14));//output
						break;
					case 6://TLO PATH B
						numOfFiles=6;
						needed.add(sourcePanels.get(1));//EOMClientBaseTLO
						needed.add(sourcePanels.get(3));//currentClientTLO
						needed.add(sourcePanels.get(7));//EDW
						needed.add(sourcePanels.get(8));//PRPackage
						needed.add(sourcePanels.get(13));//cancelPayroll
						needed.add(sourcePanels.get(14));//output
						break;
					case 7://STM A
						numOfFiles=7;
						needed.add(sourcePanels.get(0));//EOMClientBaseSTM
						needed.add(sourcePanels.get(6));//tblTLOwAS
						needed.add(sourcePanels.get(7));//EDW
						needed.add(sourcePanels.get(8));//PRPackage
						needed.add(sourcePanels.get(9));//PR Status
						needed.add(sourcePanels.get(13));//cancelPayroll
						needed.add(sourcePanels.get(14));//output
						break;
					case 8://STM B
						numOfFiles=8;
						needed.add(sourcePanels.get(0));//EOMClientBaseSTM
						needed.add(sourcePanels.get(5));//qryActive1257
						needed.add(sourcePanels.get(7));//EDW
						needed.add(sourcePanels.get(8));//PRPackage
						needed.add(sourcePanels.get(11));//qryCancelSTM
						needed.add(sourcePanels.get(12));//activeSTMCodes
						needed.add(sourcePanels.get(13));//cancelPayroll
						needed.add(sourcePanels.get(14));//output
						break;
					case 9://ESS A
						numOfFiles=7;
						needed.add(sourcePanels.get(0));//EOMClientBaseSTM
						needed.add(sourcePanels.get(6));//tblTLOwAS
						needed.add(sourcePanels.get(7));//EDW
						needed.add(sourcePanels.get(8));//PRPackage
						needed.add(sourcePanels.get(9));//PR Status
						needed.add(sourcePanels.get(13));//cancelPayroll
						needed.add(sourcePanels.get(14));//output
					default: 
						numOfFiles=7;
						break;
				}
				break;
			}
		}
		
		//copy the ID and store it in a final variable to be used in the 'Run' button actionlistener 
		final int IDf = ID;

		//Take those panels we added to 'needed' and add them to the center panel
		jp2_1.setLayout(new GridLayout(numOfFiles,1,5,0));
		
		for(JPanel p:needed)
		{
			jp2_1.add(p, BorderLayout.CENTER);
		}
		card2.add(jp2_1, BorderLayout.CENTER);
		
		//Create the north panel, the title page
		card2.add(jp2_2, BorderLayout.NORTH);
		jp2_2.setLayout(new BorderLayout(0, 5));
		JLabel title = new JLabel("SOX Audit Processor");
		JLabel text = new JLabel("Enter the path for each source file and output destination.");
		title.setFont(new Font(title.getFont().getName(),Font.BOLD,22));
		title.setHorizontalAlignment(JLabel.CENTER);
		text.setHorizontalAlignment(JLabel.CENTER);
		jp2_2.add(title,BorderLayout.CENTER);
		jp2_2.add(text,BorderLayout.SOUTH);
		
		
		//Create the south panel, which holds the buttons.
		//back2- the back button takes us back to the first page
		jp2_3.setLayout(new FlowLayout(FlowLayout.CENTER,100,20));
		back2 = new JButton("Back");
		back2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) 
			{
				((CardLayout) cards.getLayout()).show(cards, "1");
			}
			
		});
		
		//next2- The next2 button is the run button, which starts the audit.
		next2 = new JButton("Run");
		next2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) 
			{
				//get all the paths from the text fields
				ArrayList<String> paths = new ArrayList<String>();
				
				for(JPanel p : needed)
				{
					Component[] compArr = p.getComponents();
					for(Component comp : compArr)
					{
						if(comp instanceof JTextField)
						{
							paths.add(((JTextField) comp).getText());
							
						}
					}
					
				}
				
				/* (UNIMPLEMENTED PROGRESS BAR CODE, IGNORE)
				i = 0;
				t = new Timer(interval, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(i==20) {
							t.stop();
						}
						else
						{
							i++;
							pgb.setValue(i);
						}
			
						
					}
					
				});
				t.start();
				JDialog jd = new JDialog();
				jd.setSize(200,75);
				jd.setLocationRelativeTo(((Component) e.getSource()).getParent());
				jd.add(pgb);
				jd.setVisible(true);*/
				
				//Compile the audit, show the complete message, and close the program.
				Compile c = new Compile(paths,IDf);
				String[] options = {"OK"};
				JOptionPane.showOptionDialog(((Component)e.getSource()).getParent().getParent(), 
						"The Audit was successfully completed.","Complete", 
						JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
				System.exit(0);
			}
			
		});
		
		//add the buttons to the south panel
		jp2_3.add(back2);
		jp2_3.add(next2);
		card2.add(jp2_3, BorderLayout.SOUTH);

	}
	
	/**
	 * sourceFiles- takes no parameters and returns no values.
	 * 
	 * This method provides setup for the dynamic list of source files that populate based off of which audit was selected in the first page.
	 */
	public void sourceFiles()
	{
		//create the JPanels
		sourcePanels.clear();
		JPanel EOMClientBaseSTM = new JPanel();//0
		JPanel EOMClientBaseTLO = new JPanel();//1
		JPanel currentClientSTM = new JPanel();//2
		JPanel currentClientTLO = new JPanel();//3
		JPanel qryActive1152 = new JPanel();//4
		JPanel qryActive1257 = new JPanel();//5
		JPanel tblTLOwAS = new JPanel();//6
		JPanel EDW = new JPanel();//7
		JPanel PRPackage = new JPanel();//8
		JPanel PRStatus = new JPanel();//9
		JPanel qryCancelTLO = new JPanel();//10
		JPanel qryCancelSTM = new JPanel();//11
		JPanel activeSTMCodes = new JPanel();//12
		JPanel cancelPayroll = new JPanel();//13
		
		JPanel output = new JPanel();//14
	
		//add to the list
		sourcePanels.add(EOMClientBaseSTM);
		sourcePanels.add(EOMClientBaseTLO);
		sourcePanels.add(currentClientSTM);
		sourcePanels.add(currentClientTLO);
		sourcePanels.add(qryActive1152);
		sourcePanels.add(qryActive1257);
		sourcePanels.add(tblTLOwAS);
		sourcePanels.add(EDW);
		sourcePanels.add(PRPackage);
		sourcePanels.add(PRStatus);
		sourcePanels.add(qryCancelTLO);
		sourcePanels.add(qryCancelSTM);
		sourcePanels.add(activeSTMCodes);
		sourcePanels.add(cancelPayroll);
		sourcePanels.add(output);
		
		//setup their layout
		for(JPanel p : sourcePanels)
		{
			p.setLayout(new GridLayout(1,2));
			
		}
		
		//add text label
		EOMClientBaseSTM.add(new JLabel("EOM Client Base STM:"));
		EOMClientBaseTLO.add(new JLabel("EOM Client Base TLO:"));
		currentClientSTM.add(new JLabel("Today's STM Client Base:"));
		currentClientTLO.add(new JLabel("Today's TLO Client Base:"));
		qryActive1152.add(new JLabel("QryActive1152Plan:"));
		qryActive1257.add(new JLabel("QryActive1257:"));
		tblTLOwAS.add(new JLabel("tbl_TLO_plans_w_AS:"));
		EDW.add(new JLabel("TAA Billing Query:"));
		PRPackage.add(new JLabel("PR Package & Last PR Run:"));
		PRStatus.add(new JLabel("PR Status:"));
		qryCancelTLO.add(new JLabel("Qry_Cancelled_TLO_plans:"));
		qryCancelSTM.add(new JLabel("Qry_Cancelled_STM_plans:"));
		activeSTMCodes.add(new JLabel("Active 1228 1230 1289 or 1290"));
		cancelPayroll.add(new JLabel("Last Month Cancelled Payroll:"));
		output.add(new JLabel("Output Destination:"));
		
		//copy paste menu on textfield https://stackoverflow.com/questions/30682416/java-right-click-copy-cut-paste-on-textfield
		JPopupMenu menu = new JPopupMenu();
        Action cut = new DefaultEditorKit.CutAction();
        cut.putValue(Action.NAME, "Cut");
        cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        menu.add( cut );

        Action copy = new DefaultEditorKit.CopyAction();
        copy.putValue(Action.NAME, "Copy");
        copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        menu.add( copy );

        Action paste = new DefaultEditorKit.PasteAction();
        paste.putValue(Action.NAME, "Paste");
        paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        menu.add( paste );

		
		//add a blank text field
		for(JPanel p : sourcePanels)
		{
			JTextField tf = new JTextField();
			tf.setComponentPopupMenu(menu);
			p.add(tf);
			
		}
	}
	

}