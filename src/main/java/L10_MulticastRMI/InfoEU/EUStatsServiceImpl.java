package L10_MulticastRMI.InfoEU;

import java.rmi.*; // Classes and support for RMI
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*; // Classes and support for RMI servers
import java.util.Hashtable; // Contains Hashtable class

public class EUStatsServiceImpl extends RemoteServer implements EUStatsService {
	
	/* Store data in a hashtable */
	Hashtable<String, EUData> EUDbase = new Hashtable<String, EUData>();
	
	/* Constructor - set up database */
	
	EUStatsServiceImpl() throws RemoteException {
		
		EUDbase.put("France", new EUData("French", 57800000, "Paris"));
		EUDbase.put("United Kingdom", new EUData("English", 57998000, "London"));
		EUDbase.put("Greece", new EUData("Greek", 10270000, "Athens"));
		
	}
	
	/* implementazione dei metodi dell’interfaccia */
	public String getMainLanguages(String CountryName) throws RemoteException {
		
		EUData Data = (EUData) EUDbase.get(CountryName);
		
		return Data.getLangs();
		
	}
	
	public int getPopulation(String CountryName) throws RemoteException {
		EUData Data = (EUData) EUDbase.get(CountryName);
		return Data.getPop();
	}
	
	public String getCapitalName(String CountryName) throws RemoteException {
		EUData Data = (EUData) EUDbase.get(CountryName);
		return Data.getCapital();
	}
	
	//############# STEP 3: Attivazione servizio
	public static void main(String args[]) {
		try {
			System.out.println("java.rmi.server.hostname=" + System.getProperty("java.rmi.server.hostname"));
			/* Creazione di un’istanza dell’oggetto EUStatsService */
			EUStatsServiceImpl statsService = new EUStatsServiceImpl();
			
			/* Esportazione dell’Oggetto */
			//Il fatto che venga utilizzata la porta 0 significa cje export object sceglierà una porta effimera libera
			//Sulla quale mettere in attesa di richieste l'ggetto remoto
			EUStatsService stub = (EUStatsService) UnicastRemoteObject.exportObject(statsService, 0);
			//EUStatsServiceImpl stub = (EUStatsServiceImpl) UnicastRemoteObject.exportObject(statsService, 0);
			
			// Creazione di un registry sulla porta args[0]
			LocateRegistry.createRegistry(Integer.parseInt(args[0]));
			
			Registry r = LocateRegistry.getRegistry(args[0]);
			
			/* Pubblicazione dello stub (un suo riferimento) nel registry appena creato*/
			//r.rebind("//localhost:"+ args[0] +"/EUSTATS-SERVER", stub);
			r.rebind("EUSTATS-SERVER", stub);
			System.out.println("Server ready");
			
		}
		/* If any communication failures occur... */ catch (RemoteException e) {
			System.out.println("Communication error " + e.toString());
			e.printStackTrace();
		}
		
	}
	
}