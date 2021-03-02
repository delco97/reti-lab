package soluzioniProf.soluzioniLab10_Congresso_RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * RMI Service Stub
 * @author Andrea Michienzi
 * */
public interface CongressService extends Remote{

	public boolean registerSpeaker(String speaker, int session, int giornata) throws RemoteException, IllegalArgumentException;
	
	public String[][] getDailyProgram(int giornata) throws RemoteException, IllegalArgumentException;
	
	public String printProgram() throws RemoteException;

	
}
