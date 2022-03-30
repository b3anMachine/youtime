package com.beans;

import java.awt.Desktop;
import java.net.URI;
import java.util.*;
import javax.swing.*;
import java.awt.*; 
import java.text.*; 
import java.awt.event.*;
import javax.swing.border.*;

public class YouTest{
	//The main frame and panel 
	private JFrame frame = new JFrame("YouTime");
	private JPanel mainPanel = new JPanel();
	
	//The widget panel. The widgets will all go on this panel to give them a nice border within the window. 
	private JPanel widgetPanel = new JPanel();
	
	//The widget panels, one for each row of widgets on the frame
	//These also serve the purpose of being left justified, so that widgets show up left justified on the main panel
	//The textfield widget will not get a panel so that it stretches across the entire window
	private JPanel timePanel = new JPanel();
	private JPanel headerPanel = new JPanel();
	private JPanel URLPanel = new JPanel();
	private JPanel timeTextPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	//Declaring the widgets
	private JLabel headerLabel;
	private JLabel URLLabel;
	private JTextField textField;
	private JLabel timeLabel;
	private JComboBox hours;
	private JComboBox mins;
	private JComboBox ampm;
	private JButton button;
	private JLabel confirmLabel;

	//Boolean to know whether YouTime has been set
	private boolean set;
	
	/*
	private String uri = "http://www.youtube.com/watch?v=8wJKJP78DYQ";
	Desktop desktop = Desktop.getDesktop();
	*/
	
	Desktop desktop = Desktop.getDesktop();
	java.util.Timer timer = new java.util.Timer();
	String uri = "http://www.youtube.com/watch?v=PYP_4HWaa24";
	Task task = new Task();
	long fireMil;
	GregorianCalendar fire = new GregorianCalendar();
	Date fireDate;


	public static void main(String[] args){
		//System.out.println(new java.sql.Date(2014, 01, 21));
		new YouTest().bigbang();
		//System.out.print(new YouTime().mns[2]);
	}

	private void bigbang(){	
		buildGUI();
	
	/*

		*/
	}

	
public void buildGUI(){
		//Initialize widgets:
			//Header label, name of the app in big letters
		headerLabel = new JLabel("YouTime");	
		headerLabel.setFont(new Font("Futura", Font.PLAIN,40));
			//URL Label to describe what to put in the textfield
		URLLabel = new JLabel("Enter URL to open with default browser:");
			//The textfield for the URL
	 	textField = new JTextField();
	 		//The time label to describe what time to input
		timeLabel = new JLabel("Enter next time to open:");
			//Ready the time arrays for the time comboboxes
		String[] str = {"am", "pm"}; 
		Integer[] hrs = {1,2,3,4,5,6,7,8,9,10,11,12}; 
		String[] mns = new String[60];
		DecimalFormat df = new DecimalFormat("00");
		for(int i=0; i<60; ++i)
			mns[i] = df.format(i);
			//Initialize the comboboxes with the time arrays
		hours = new JComboBox(hrs);
		hours.setSelectedIndex(11);
		mins = new JComboBox(mns);
		ampm = new JComboBox(str);
			//Initialize button and add action listener
		button = new JButton("Set It");
		button.addActionListener(new MySetListener());
			//Initialize label 
		confirmLabel = new JLabel();
		
		//Setup widget panels with left justified flowlayout
		headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		URLPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		timeTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		timePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		
		//Add widgets to their respective widget panels
		headerPanel.add(headerLabel);
		URLPanel.add(URLLabel);
		timeTextPanel.add(timeLabel);
		timePanel.add(hours);
		timePanel.add(mins);
		timePanel.add(ampm);
		buttonPanel.add(button);
		buttonPanel.add(confirmLabel);
		
		//change the background color of the widget panels that will reside within the widget panel
		URLPanel.setBackground(new Color(225,225,225));
		timeTextPanel.setBackground(new Color(225,225,225));
		timePanel.setBackground(new Color(225,225,225));
		buttonPanel.setBackground(new Color(225,225,225));
		
		//Setup widget panels
		widgetPanel.setLayout(new BoxLayout(widgetPanel, BoxLayout.Y_AXIS));
		widgetPanel.setBackground(new Color(225,225,225));
		widgetPanel.setBorder(new LineBorder(new Color(199, 199, 199), 2, true));
		
		widgetPanel.add(Box.createRigidArea(new Dimension(0,10)));
		widgetPanel.add(URLPanel);
		widgetPanel.add(textField);
		widgetPanel.add(Box.createRigidArea(new Dimension(0,25)));
		widgetPanel.add(timeTextPanel);
		widgetPanel.add(timePanel);
		widgetPanel.add(Box.createRigidArea(new Dimension(0,20)));
		widgetPanel.add(buttonPanel);
		
		//Set up main panel with boxlayout and aesthetic empty border. 
		//Add header and widget panel to it. 
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		mainPanel.add(headerPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0,5)));
		mainPanel.add(widgetPanel);
		mainPanel.setBackground(new Color(234, 234, 234));
		
		//Setting up the frame with size, location, and adding the main panel to it
		int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		frame.setLocation(screenWidth/4, screenHeight/4);
		frame.getContentPane().add(BorderLayout.NORTH,mainPanel);
        frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(600,330);
	}
	
	public void set(){
		if(!set){
			if(!(textField.getText() == ""))
				//uri = textField.getText();

			fire.set(2014, 0, 21, 12, 23);
		long fireMil = fire.getTimeInMillis();
		System.out.println(fireMil);
		
		fireDate = new Date(fireMil);
		timer.schedule(task,fireDate);
			
			textField.setEnabled(false);
			hours.setEnabled(false);
			mins.setEnabled(false);
			ampm.setEnabled(false);
			button.setText("Reset it");
			confirmLabel.setText("Set. Keep the window open, minimize if you'd like.");
			set = true;
		}
		else{
			textField.setEnabled(true);
			hours.setEnabled(true);
			mins.setEnabled(true);
			ampm.setEnabled(true);
			button.setText("Set it");
			confirmLabel.setText("");
			set = false;
		}
	}
	
	class Task extends TimerTask {
	
		public void run(){
		try{
			desktop.browse(new URI(uri));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		}
	
	}
	
	class MySetListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			set();
		}
	}

}