package L04_Monitor.CMRaceCondition;

import java.util.concurrent.ConcurrentHashMap;

public class CMRaceCondition {
	
	public static void main(String[] args) throws Exception {
		ConcurrentHashMap<String, String> chm = new ConcurrentHashMap<String, String>();
		chm.put("Roma", "Italia");
		chm.put("Berlino", "Germania");
		chm.put("Parigi","Francia"); System.out.println("La mappa iniziale è: " + chm);
		Thread PutThread = new RaceThread(chm);
		Object value = chm.get("Londra");
		boolean notFound = false;
		if (value == null) {
			notFound = true;
			PutThread.start();
			Thread.sleep(10000); //il thread va in esecuzione e non trova Londra nella Map
			chm.put("Londra", "Great Britain");
		}
		PutThread.join();
		if (notFound) {
			System.out.println("Sono il main ed ho inserito il valore" + "Londra=Great Britain");
		}
		
		System.out.println("E la mappa ora contiene " + chm);
		//Londra = UK perché PutThread modifica dopo a main thread.
		//Race condition: valore associato a chiave Londra dipende da chi arriva prima/dopo.
	}
	
}

