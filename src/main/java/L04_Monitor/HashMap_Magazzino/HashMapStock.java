package L04_Monitor.HashMap_Magazzino;

import java.util.concurrent.ConcurrentHashMap;

public class HashMapStock implements Runnable {
	
	ConcurrentHashMap<String, String> cMap = new ConcurrentHashMap<String, String>();
	
	public HashMapStock(ConcurrentHashMap<String, String> cMap) {this.cMap = cMap;}
	
	public void run() {
		cMap.putIfAbsent("Socks", "in stock");
		System.out.println("Socks in stock");
		cMap.putIfAbsent("Shirts", "in stock");
		System.out.println("Shirts in stock");
		cMap.putIfAbsent("Pants", "in stock");
		System.out.println("Pants in stock");
		cMap.putIfAbsent("Shoes", "in stock");
		System.out.println("Shoes in stock");
	}
	
}