package seilbahn.control;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import seilbahn.Seilbahn;
import lejos.hardware.Audio;
import lejos.hardware.Sound;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.utility.Delay;

public class STG implements Runnable{
	private Seilbahn seilbahn;
	private RMIRegulatedMotor mainEngine;
	private RemoteEV3 ev3;
	
	/*
	 * 0 - Off
	 * 1 - Starting
	 * 2 - Running
	 * 3 - Running with stop command
	 * 4 - Emergency stop
	 */
	private int engineState = 0;
	private int speed = 2;
	
	public STG(Seilbahn seilbahn){
		this.seilbahn = seilbahn;
	}
	
	public void run() {
		startSTG();
		while(seilbahn.applicationRunning){
			//Engine state
			if(engineState == 1) startSeilbahn();
			else if(engineState == 3) stopSeilbahn();
			else if(engineState == 4) stopEmergency();
			decideSpeed();
		}		
    }	

	public void setStart(){
		if(seilbahn.getHornReport()) engineState = 1;
	}
	
	public void setStop(){
		engineState = 3;
	}
	
	public void setEmergencyStop(){
		engineState = 4;
	}	
	

	public void setSpeed(int speed) {
		this.speed = speed;
	}	
	
	private void startSTG(){
		System.out.println("Starting STG");
		try {
			ev3 = new RemoteEV3("192.168.10.26");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ev3.setDefault();
		mainEngine = ev3.createRegulatedMotor("A", 'M');
		System.out.println("STG Connected");
	}
	
	private void decideSpeed() {
			try {
				int currentSpeed = mainEngine.getSpeed();
				int newSpeed;
				if(speed == 0) newSpeed = 200;
				else newSpeed = speed * 525;
				
				if(currentSpeed != newSpeed){
					mainEngine.setAcceleration(50);
					mainEngine.setSpeed(newSpeed);
					System.out.println("Setting new speed "+newSpeed);
				}
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private void startSeilbahn(){
		try {
			for (int i = 0; i < 5; i++) {
				Sound.beep();		
				Delay.msDelay(350);				
			}
			
			mainEngine.setSpeed(200);
			mainEngine.forward();	
			Delay.msDelay(4000);
			mainEngine.setAcceleration(50);
			engineState = 2;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
	
	private void stopSeilbahn(){
		try {
			mainEngine.setAcceleration(100);
			mainEngine.stop(true);
			seilbahn.setHornReport(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		engineState = 0;
	}
	
	private void stopEmergency(){
		try {
			mainEngine.setAcceleration(650);
			mainEngine.stop(true);	
			seilbahn.setHornReport(false);			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		engineState = 0;
	}
	
	public void totalTerminate(){
		System.out.println("Terminating seilbahn");
		try {
			if(mainEngine != null){
				mainEngine.setAcceleration(6000);
				if(mainEngine.getSpeed() != 0) mainEngine.stop(true);
				mainEngine.close();
				Audio sound = ev3.getAudio();
				sound.systemSound(3);				
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
