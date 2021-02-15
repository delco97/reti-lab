package L04_Monitor.Dropbox;

import java.util.Random;

public class Consumer implements Runnable {
	Dropbox d;
	boolean p;
	static final int MIN = 1;
	static final int MAX = 50;
	public Consumer(Dropbox d, boolean p) {
		this.d = d;
		this.p = p;
	}
	
	@Override
	public void run() {
		d.take(p);
	}
}
