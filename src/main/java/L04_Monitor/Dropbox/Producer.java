package L04_Monitor.Dropbox;

import java.util.Random;

public class Producer implements Runnable {
	Dropbox d;
	static final int MIN = 1;
	static final int MAX = 50;
	public Producer(Dropbox d) {
		this.d = d;
	}
	
	@Override
	public void run() {
		d.put(new Random().nextInt((MAX - MIN) + 1) + 1);
	}
	
}
