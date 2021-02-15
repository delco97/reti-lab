package L04_Monitor.Dropbox;

public class MainClass {
	
	public static void main(String[] args) {
/*		Dropbox dbox = new Dropbox();
		final int nConsumers = 3;
		final int nProducers = 1;
		
		for(int i=0; i<nConsumers; i++)
			new Thread(new Consumer(dbox, true));
		
		for(int i=0; i<nProducers; i++)
			new Thread(new Producer(dbox));*/
		Dropbox db= new Dropbox();
		
		(new Thread(new Consumer(db,false ))).start(); // consuma numeri dispari
		
		(new Thread(new Consumer(db, false))).start(); //consuma numeri dispari
		
		(new Thread(new Consumer(db, true))).start(); //consuma numeri pari
		
		(new Thread(new Producer(db))).start();
		
	}
	
}
