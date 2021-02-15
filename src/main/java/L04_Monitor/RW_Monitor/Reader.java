package L04_Monitor.RW_Monitor;

public class Reader extends Thread {
	
	RWMonitor RWM;
	int i;
	
	public Reader(RWMonitor RWM, int i) {
		this.RWM = RWM;
		this.i = i;
	}
	
	public void run() {
		while (true) {
			RWM.StartRead();
			try {
				Thread.sleep((int) Math.random() * 1000);
				System.out.println("Lettore" + i + "sta leggendo");
			} catch (Exception e) {
			}
			;
			RWM.EndRead();
		}
	}
	
}
