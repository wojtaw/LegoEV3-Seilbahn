package seilbahn;

import seilbahn.control.STG;
import seilbahn.gui.ControlRoom;


public class Seilbahn{
	public boolean applicationRunning = true;
	
	private ControlRoom controlRoom;
	private STG stg;
	
	
	public Seilbahn(){
		stg = new STG(this);
		Thread stgThread = new Thread(stg);
		stgThread.start();
		
		controlRoom = new ControlRoom(this);		
	}
	
	public void startSeilbahn(){
		stg.setStart();
	}
	
	public void forceQuit(){
		stg.totalTerminate();
		applicationRunning = false;
	}
	
}
