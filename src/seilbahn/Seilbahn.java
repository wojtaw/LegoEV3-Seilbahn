package seilbahn;

import seilbahn.control.STG;
import seilbahn.gui.ControlRoom;


public class Seilbahn{
	public boolean applicationRunning = true;
	
	private ControlRoom controlRoom;
	private STG stg;
	private boolean hornReport = false;	
	
	
	public Seilbahn(){
		stg = new STG(this);
		Thread stgThread = new Thread(stg);
		stgThread.start();
		
		controlRoom = new ControlRoom(this);		
	}
	
	public void startSeilbahn(){
		stg.setStart();
	}
	
	public void stopSeilbahn(){
		stg.setStop();
	}
	
	public void emergencyStop(){
		stg.setEmergencyStop();
	}
	
	public void changeSpeed(int speed){
		stg.setSpeed(speed);
	}
	
	public void forceQuit(){
		stg.totalTerminate();
		applicationRunning = false;
	}
	
	public void setHornReport(boolean horn){
		if(!horn){
			controlRoom.disableStart();
		}
		hornReport = horn;
	}
	
	public boolean getHornReport(){
		return hornReport;
	}
	
	
}
