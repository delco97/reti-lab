package L02_Threadpools.Es1_Es2;

public class MainClass {
	
	public static void main(String[] args) throws InterruptedException {
		Station s = new Station(5);
		int numTravelers = 50;
		for(int i=0; i < numTravelers; i++){
			Thread.sleep(50);
			s.tryAddTraveler(new Traveler());
		}
		s.endStation(10000);
		System.out.printf("Numero viaggiatori serviti: %d", s.getTravelersServed());
	}
}
