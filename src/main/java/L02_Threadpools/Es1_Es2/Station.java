package L02_Threadpools.Es1_Es2;

import java.util.concurrent.*;

public class Station {
	private int numDesk = 5;
	private int maxNumPeopleInside = 10;
	private ThreadPoolExecutor poolExe;
	
	public int getTravelersServed() {
		return travelersServed;
	}
	
	private int travelersServed = 0;
	
	public Station(int p_numDesk) {
		numDesk =  p_numDesk;
		poolExe = new ThreadPoolExecutor(
				numDesk, //number of core threads
				numDesk, //max number of thread
				0L, //time
				TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<>(maxNumPeopleInside)
		);
	}
	
	public void tryAddTraveler(Traveler travelerTask) {
		System.out.printf("Arrivato Viaggiatore {%d}\n", travelerTask.getId());
		try {
			poolExe.execute(travelerTask);
			travelersServed++;
		} catch(RejectedExecutionException e) {
			System.out.printf("Viaggiatore {%d}: sala al completo, non può essere servito.\n", travelerTask.getId());
		}
	}
	
	public void endStation(long timeout) {
		poolExe.shutdown();
		
		while(poolExe.isTerminating()) {
			try {
				poolExe.awaitTermination(timeout, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				System.out.println("Interrotta awaitTermination");
			}
		}
		
	}
	
}
