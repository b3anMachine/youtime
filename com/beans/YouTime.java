package com.beans;

import java.awt.Desktop;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*; 
import java.text.*; 
import java.awt.event.*;
import javax.swing.border.*;
import java.util.concurrent.*;
import java.net.*;
import java.io.*;

public class YouTime{
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
	
	//String and Desktop RECOMMENT ME
	private String uri = "http://www.youtube.com/watch?v=PYP_4HWaa24";
	private Desktop desktop = Desktop.getDesktop();

	java.util.Timer timer = new java.util.Timer();
	
	Date alarmTime;
	String title;

	public static void main(String[] args){
		new YouTime().bigbang();
	}

	private void bigbang(){
		buildGUI();
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
			if(!(textField.getText().equals("")))
				uri = textField.getText();
			textField.setText(uri);
			
			if(false){
				set = true;
				set();
				confirmLabel.setText("Invalid URL");
			}
			else {
			timer = new java.util.Timer();
			Task task = new Task();
		
			calcAlarmTime();
			findTitle();		
			timer.schedule(task,alarmTime);
			frame.setTitle("Set for " + alarmTime + " : " + title);
			
			textField.setEnabled(false);
			hours.setEnabled(false);
			mins.setEnabled(false);
			ampm.setEnabled(false);
			button.setText("Reset it");
			confirmLabel.setText("Set. Keep the window open, minimize if you'd like.");
			set = true;
			}
		}
		else{
			frame.setTitle("YouTime");
			timer.cancel();
			textField.setEnabled(true);
			hours.setEnabled(true);
			mins.setEnabled(true);
			ampm.setEnabled(true);
			button.setText("Set it");
			confirmLabel.setText("");
			set = false;
		}
	}
	
	/*
	public boolean urlValid(){
		 HttpURLConnection connection = null;
		try{         
  		  URL myurl = new URL("http://youtube.com");        
  		  connection = (HttpURLConnection) myurl.openConnection(); 
  		  //Set request to header to reduce load.      
  		  connection.setRequestMethod("HEAD");         
 	   	  int code = connection.getResponseCode();        
  		  return true;
		} catch (Exception e){
		    return false;
			}
	}
	*/
		
	public void calcAlarmTime(){
			GregorianCalendar now = new GregorianCalendar();
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH);
			int monthDay = now.get(Calendar.DAY_OF_MONTH);
			
			int meridiem = 0;
			if(ampm.getSelectedItem().equals("pm"))
				meridiem = meridiem + 12;		
			int hour = (Integer) hours.getSelectedItem() + meridiem;
			int min = Integer.parseInt((String) mins.getSelectedItem());
	
			GregorianCalendar alarmToday = new GregorianCalendar(year, month, monthDay, hour, min, 00);
			
			if(alarmToday.getTimeInMillis() > System.currentTimeMillis()){
				alarmTime = new Date(alarmToday.getTimeInMillis());}
			else{
				alarmTime = new Date(alarmToday.getTimeInMillis() + TimeUnit.DAYS.toMillis(1));}
	}
	
	public void findTitle(){
			if(uri.length()>7 && !uri.substring(0,7).equals("http://"))
				uri = "http://" + uri;
			
			try{
			title = "No Title";
			URL oracle = new URL(uri);
			System.out.println(uri);
     	    BufferedReader in = new BufferedReader(
     	    new InputStreamReader(oracle.openStream()));
			
    	    String inputLine;
     	    while ((inputLine = in.readLine()) != null)
         	   if(inputLine.indexOf("<title>") != -1){
      	          title = inputLine.substring(inputLine.indexOf("<title>") + "<title>".length(),inputLine.indexOf("</title>"));
            	    break;}            
      		 in.close();
			}
			catch(Exception e){e.printStackTrace();}
	}
	
	
	class Task extends TimerTask {
	
		public void run(){
		try{	
			desktop.browse(new URI(uri));
			set();
		}
		catch(Exception e){
			e.printStackTrace();
			//nope
			set();
			confirmLabel.setText("Invalid URL");
		}
		}
	
	}
	
	
	class MySetListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			set();
		}
	}
}