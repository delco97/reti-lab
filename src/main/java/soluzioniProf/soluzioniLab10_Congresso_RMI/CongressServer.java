package soluzioniProf.soluzioniLab10_Congresso_RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/*
 * CongressServer
 * 
 * Server che espone lo stub remoto CongressTable
 * @author Andrea Michienzi
 * */
public class CongressServer {
		
	public static void main(String[] args) {
		
		try{
			// Creazione di un'istanza dell'oggetto CongressTable
			CongressTable congress = new CongressTable();
			
			CongressService stub = (CongressService) UnicastRemoteObject.exportObject(congress, 0);
			
			// Creazione di un registry sulla porta 30000
			LocateRegistry.createRegistry(30000);
			Registry r = LocateRegistry.getRegistry(30000);
			
			// Pubblicazione dello stub nel registry
			r.rebind("CONGRESS-SERVER", stub);
			
			System.out.println("Server ready");
		}
		catch(RemoteException e){
			e.printStackTrace();
		}
	}
}
