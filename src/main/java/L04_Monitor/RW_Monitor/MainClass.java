package L04_Monitor.RW_Monitor;

public class MainClass {
	
	public static void main(String args[]) {
		RWMonitor RWM = new RWMonitor();
		for (int i = 1; i < 10; i++) {
			Reader r = new Reader(RWM, i);
			Writer w = new Writer(RWM, i);
			r.start();
			w.start();
		}
	}
}
