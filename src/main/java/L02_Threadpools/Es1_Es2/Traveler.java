package L02_Threadpools.Es1_Es2;

import java.util.concurrent.ThreadLocalRandom;

public class Traveler implements Runnable {
	static int counter = 0;
	
	public int getId() {
		return id;
	}
	
	private int id;
	static final int MIN_BUY_TIME = 0;
	static final int MAX_BUY_TIME = 1000;
	
	public Traveler() {
		id = counter++;
	}
	
	@Override
	public void run() {
		System.out.printf("Traveler {%d}: sto acquistando un biglietto...\n", id);
		long buyTime = ThreadLocalRandom.current().nextInt(MIN_BUY_TIME, MAX_BUY_TIME + 1);
		try {
			Thread.sleep(buyTime);
			System.out.printf("Traveler {%d}: ha acquistato il biglietto...\n", id);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
