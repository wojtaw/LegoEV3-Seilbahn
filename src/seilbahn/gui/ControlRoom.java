package seilbahn.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

import seilbahn.Seilbahn;
import seilbahn.audio.AudioPlayer;

public class ControlRoom extends JFrame implements ActionListener{
	Seilbahn seilbahn;
	JButton startButton,stopButton,emergencyStopButton, hornButton;
	JRadioButton speed1,speed2,speed3,speed4;
	
	public ControlRoom(Seilbahn seilbahn){
		this.seilbahn = seilbahn;
	    setTitle("STG");
	    setSize(800, 600);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	    setLayout(new FlowLayout());
	   
	    initGUI();
	    addListeners();
	}

	private void addListeners() {
		startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               	System.out.println("Starting");
               	seilbahn.startSeilbahn();
            }
        });
		
		stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               	System.out.println("Stoping");
               	seilbahn.stopSeilbahn();
            }
        });
		
		emergencyStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               	System.out.println("Emergency stoping");
               	seilbahn.emergencyStop();
            }
        });
		
		hornButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               	System.out.println("Emergency stoping");
               	seilbahn.hornReport = true;
               	AudioPlayer.playHorn();
               	enableStart();
            }
        });		
				
		
		this.addWindowListener(new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    {
		    	seilbahn.forceQuit();
		    }
		});
		
	}
	
	private void disableStart(){
		startButton.setEnabled(false);
	}
	
	private void enableStart(){
		startButton.setEnabled(true);
	}	

	private void initGUI() {
	    startButton = new JButton("Start");
	    
	    stopButton = new JButton("Stop");
	    hornButton = new JButton("Horn");
	    emergencyStopButton = new JButton("EMERGENCY STOP");
	    
	    
	    speed1 = new JRadioButton("Speed 1");
	    speed1.setActionCommand("0");
	    speed1.addActionListener(this);
	    
	    speed2 = new JRadioButton("Speed 2");
	    speed2.setActionCommand("1");
	    speed2.addActionListener(this);
	    
	    speed3 = new JRadioButton("Speed 3");
	    speed3.setActionCommand("2");
	    speed3.setSelected(true);	    
	    speed3.addActionListener(this);
	    
	    speed4 = new JRadioButton("Speed 4");
	    speed4.setActionCommand("3");
	    speed4.addActionListener(this);

	    ButtonGroup speedGroup = new ButtonGroup();
	    speedGroup.add(speed1);
	    speedGroup.add(speed2);
	    speedGroup.add(speed3);
	    speedGroup.add(speed4);
	    
	    this.add(hornButton);
	    this.add(startButton);	    
	    this.add(stopButton);
	    this.add(speed1);
	    this.add(speed2);
	    this.add(speed3);
	    this.add(speed4);
	    this.add(emergencyStopButton);

	    disableStart();	    
	}  
	
	public void actionPerformed(ActionEvent e) {
	    System.out.println("Clicked "+e.getActionCommand());
	    seilbahn.changeSpeed(Integer.parseInt(e.getActionCommand()));
	}	
}
