package L04_Monitor.RW_Monitor;

public class Writer extends Thread {
	
	RWMonitor RWM;
	int i;
	
	public Writer(RWMonitor RWM, int i) {
		this.RWM = RWM;
		this.i = i;
	}
	
	public void run() {
		while (true) {
			RWM.StartWrite();
			try {
				Thread.sleep((int) Math.random() * 1000);
				System.out.println("Scittore" + i + "sta scrivendo");
			} catch (Exception e) {
			}
			;
			RWM.EndWrite();
		}
	}
	
}

