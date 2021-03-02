package soluzioniProf.soluzioniLab10_Congresso_RMI;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;

/*
 * CongressTable
 * Estende RemoteServer ed implementa l'interfaccia Remote "CongressService"
 * 
 * @author Andrea Michienzi
 * 
 * */
public class CongressTable  extends RemoteServer implements CongressService {

	private static final long serialVersionUID = 1L;

	// Tabella sessione/interventi
	private String[][][] table;
	
	public static final int DAYS = 3;
	public static final int PARTICIPATIONS = 5;
	public static final int SESSIONS = 12;
	
	public static final String FREE = "<Free>";
		
	public CongressTable() {
				
		table = new String[PARTICIPATIONS][SESSIONS][DAYS];
		
		for(int z=0; z<DAYS; z++)
			for(int x=0; x<PARTICIPATIONS; x++)
				for(int y=0; y<SESSIONS; y++)
					table[x][y][z] = FREE;
	}
	
	public boolean registerSpeaker(String speaker, int session, int day) throws RemoteException, IllegalArgumentException {
		
		if(speaker == null 
				|| session < 0 || session >= SESSIONS
				|| day < 0 || day >= DAYS)
			throw new IllegalArgumentException("Invalid day or session");
		
		for(int i=0; i<PARTICIPATIONS; i++)
			
			if(table[i][session][day] == FREE){
				
				table[i][session][day] = new String(speaker);
				return true;
			}
		
		return false;
	}
	
	public String[][] getDailyProgram(int day) throws RemoteException, IllegalArgumentException{
		
		if(day < 0 || day >= DAYS)
			throw new IllegalArgumentException();
		
		String[][] resTable = new String[PARTICIPATIONS][SESSIONS];
		
		for(int i=0; i<PARTICIPATIONS; i++)
			for(int j=0; j<SESSIONS; j++)
				if(table[i][j][day] == FREE)
					resTable[i][j] = new String(table[i][j][day]);
			
		return resTable;
	}
	
	public String printProgram(){
		
		String program = new String();
		
		for(int z=0; z<DAYS; z++){

			program += "Programma giornata " + z + " \n\n";

			for(int i=0; i<SESSIONS; i++){
				for(int j=0; j<PARTICIPATIONS; j++){
					program += table[j][i][z] + " ";
				}
				program += "\n";
			}
			program += "\n";
		}
		
		return program;
	}
}
