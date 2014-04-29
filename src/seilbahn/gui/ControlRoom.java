package seilbahn.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import seilbahn.Seilbahn;

public class ControlRoom extends JFrame{
	Seilbahn seilbahn;
	JButton startButton;
	JButton stopButton;
	
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
               	System.out.println("Starting");
               	seilbahn.stopSeilbahn();
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

	private void initGUI() {
	    startButton = new JButton("Start");
	    stopButton = new JButton("Stop");
	    this.add(startButton);	    
	    this.add(stopButton);
	}  
}
