/**
 * Title: UserInterface.java
 * Abstract: Stage IV is implemented 
 * with the Swing(GUI toolkit for Java).
 * 
 * 
 * Author: Arturo Lopez
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream.GetField;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class UserInterface {
	public static void main(String args[])
	{
		JFrame frame = new Date();
		frame.setVisible(true);
	}
}

class Date extends JFrame{
	
	public Date()
	{	
		//set the GUI's title,size,and panel
		this.setTitle("Date Game");
		this.setSize(290,220);
		centerWindow(this);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new DateGame();
		this.add(panel);

		// Creates a menubar for a JFrame
        JMenuBar menuBar = new JMenuBar();
        
        // Add the menubar to the frame
        setJMenuBar(menuBar);
        
        // Define and add two drop down menu to the menubar
        JMenu fileMenu = new JMenu("Options");
        menuBar.add(fileMenu);
        
        
        // Create and add simple menu item to one of the drop down menu
        JMenuItem about = new JMenuItem("About");
        JMenuItem exitAction = new JMenuItem("Exit");
        
        
        // Create a ButtonGroup and add both radio Button to it. Only one radio
        // button in a ButtonGroup can be selected at a time.
        fileMenu.add(about);
        fileMenu.addSeparator();
        fileMenu.add(exitAction);
      
        // Add a listener to the New menu item. actionPerformed() method will
        // invoked, if user triggred this menu item
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	String message = "Stage IV of the CODE2040 API Challenge begins by the API giving you a dictionary. "
            			+ "\nThe values received in the dictionary are an ISO 8601 datestamp and an interval\nconsisting with a number of seconds."
            			+ " You add the interval to the date, then return\nthe resulting date to the API.\n\nCreated by Arturo Lopez";
            	JOptionPane.showMessageDialog(getParent(), message);
            }
        });
        
        exitAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        
		
	}
	//centers the window for best fit
	private void centerWindow(Window w) { 
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		setLocation((d.width - w.getWidth())/2, (d.height-w.getHeight())/2);
	}
}

class DateGame extends JPanel implements ActionListener{
	//variable declaration for Swing
	private String datestamp;
	private String interval_str;
	private String final_datestamp;
	private String status;
	
	private JTextField datestamp_textfield,
					   updatedDate_textfield,
					   interval_textfield,
					   status_textfield;
	
	private JLabel datestamp_label,
				   updatedDate_label,
				   interval_label,
				   status_label;
	
	private JButton updateDateButton,
					exitButton;
	
	public DateGame()
	{
		//create instance to acquire the data from the API
		ISOString test = new ISOString("dLcGAO9jnJ","http://challenge.code2040.org/api/time");
		test.makeHTTPPOSTRequest();
		
		//datestamp and interval are stored 
		datestamp = ISOString.getDate();
		interval_str = Integer.toString(ISOString.getInterval());
		
		//create panel for the labels and textfields
		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		datestamp_label = new JLabel("Datestamp: ");
		displayPanel.add(datestamp_label);
		
		datestamp_textfield = new JTextField(15);
		datestamp_textfield.setEditable(false);
		datestamp_textfield.setFocusable(false);
		datestamp_textfield.setText(datestamp);
		displayPanel.add(datestamp_textfield);
		
		interval_label = new JLabel("Interval:      ");
		displayPanel.add(interval_label);
		
		interval_textfield = new JTextField(15);
		interval_textfield.setEditable(false);
		interval_textfield.setFocusable(false);
		interval_textfield.setText(interval_str);
		displayPanel.add(interval_textfield);
		
		
		updatedDate_label = new JLabel("New Date:   ");
		updatedDate_label.setVisible(false);
		displayPanel.add(updatedDate_label);
		
		updatedDate_textfield = new JTextField(15);
		updatedDate_textfield.setEditable(false);
		updatedDate_textfield.setFocusable(false);
		updatedDate_textfield.setVisible(false);
		displayPanel.add(updatedDate_textfield);
		
	
		status_label = new JLabel("Status:        ");
		status_label.setVisible(false);
		displayPanel.add(status_label);
		
		status_textfield = new JTextField(15);
		status_textfield.setEditable(false);
		status_textfield.setFocusable(false);
		status_textfield.setVisible(false);
		displayPanel.add(status_textfield);
		
		
		// creates panel for the convert and exit buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		updateDateButton = new JButton("Convert");
		updateDateButton.addActionListener(this);
		buttonPanel.add(updateDateButton);
		
		// exit button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton);
        
        //displays the two panels into the GUI
        this.setLayout(new BorderLayout());
		this.add(displayPanel,BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
		
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		//if the user presses the exit button the GUI closes
		if(source == exitButton)
			System.exit(0);
		else if (source == updateDateButton) // if convert button is clicked then display the new time and status code
		{
			final_datestamp = ISOString.updateDate();
			status = ISOString.getStatus();
			
			updatedDate_label.setVisible(true);
			updatedDate_textfield.setVisible(true);
			updatedDate_textfield.setText(final_datestamp);
			
			status_label.setVisible(true);
			status_textfield.setVisible(true);
			status_textfield.setText(status);
			updateDateButton.setEnabled(false);
		}
	}
}