package L04_Monitor.Iterators;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;

public class FailSafeItr {
	
	public static void main(String[] args) {
		ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String, Integer>();
		map.put("A", 1);
		map.put("B", 2);
		map.put("C", 3);
		map.put("D", 4);
		Iterator<String> it = map.keySet().iterator(); //iterazione delle chiavi fatta in rdine alfabetico
		while (it.hasNext()) {
			String key = (String) it.next();
			System.out.println(key + " : " + map.get(key)); // Notice, it has NOT created separate copy
			// It will print 7
			map.put("E", 7);
		}
	}
	
}
// the program prints:
//A : 1
//B : 2
//C : 3
//D : 4
//E : 7