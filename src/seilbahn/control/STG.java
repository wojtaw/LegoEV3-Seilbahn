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
	 */
	private int engineState = 0;
	private int speed = 3;
	
	public STG(Seilbahn seilbahn){
		this.seilbahn = seilbahn;
	}
	
	public void run() {
		startSTG();
		while(seilbahn.applicationRunning){
			if(engineState == 1) startSeilbahn();
			if(engineState == 3) stopSeilbahn();
		}		
    }	
	
	public void setStart(){
		engineState = 1;
	}
	
	public void setStop(){
		engineState = 3;
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
	
	private void startSeilbahn(){
		try {
			for (int i = 0; i < 5; i++) {
				Sound.beep();		
				Delay.msDelay(350);				
			}
			
			mainEngine.setSpeed(200);
			mainEngine.forward();	
			Delay.msDelay(5000);
			mainEngine.setAcceleration(50);
			mainEngine.setSpeed(speed * 500);
			engineState = 2;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
	
	private void stopSeilbahn(){
		try {
			mainEngine.setAcceleration(50);
			mainEngine.stop(false);		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void totalTerminate(){
		System.out.println("Terminating seilbahn");
		try {
			if(mainEngine != null){
				mainEngine.setAcceleration(6000);
				mainEngine.stop(true);
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
