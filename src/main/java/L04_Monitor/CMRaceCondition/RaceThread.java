package L04_Monitor.CMRaceCondition;

import java.util.concurrent.ConcurrentHashMap;

public class RaceThread extends Thread {
	
	ConcurrentHashMap<String, String> chm;
	
	public RaceThread(ConcurrentHashMap<String, String> chm) {this.chm = chm; }
	
	;
	
	public void run() {
		Object value = chm.get("Londra");
		if (value == null) {
			try {
				Thread.sleep(50000);
				// do la possibilità al Main di inserire nella Map il Valore Great Britain // ma quando mi risveglio, aggiorno Great Britain con UK
			} catch (Exception e) {
			}
			chm.put("Londra", "UK");
		}
	}
	
}
