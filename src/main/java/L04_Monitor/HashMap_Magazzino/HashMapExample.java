package L04_Monitor.HashMap_Magazzino;

import java.util.concurrent.*;

public class HashMapExample {
	
	public static void main(String args[]) {
		ConcurrentHashMap<String, String> concurrentMap = new ConcurrentHashMap<String, String>();
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		executor.execute(new HashMapStock(concurrentMap));
		executor.execute(new HashMapEmpty(concurrentMap));
		executor.execute(new HashMapReplace(concurrentMap));
	}
	
}
