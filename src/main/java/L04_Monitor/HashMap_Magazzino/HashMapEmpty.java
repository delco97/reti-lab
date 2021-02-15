package L04_Monitor.HashMap_Magazzino;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.*;

public class HashMapEmpty implements Runnable {
	
	ConcurrentHashMap<String, String> cMap = new ConcurrentHashMap<String, String>();
	
	public HashMapEmpty(ConcurrentHashMap<String, String> cMap) {this.cMap = cMap;}
	
	public void run() {
		while (true) {
			Iterator<String> i = cMap.keySet().iterator();
			while (i.hasNext()) {
				String s = i.next();
				if (cMap.replace(s, "in stock", "sold out")) {
					System.out.println(s + "sold out");
				}
				try {
					Thread.sleep(new Random().nextInt(500));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}