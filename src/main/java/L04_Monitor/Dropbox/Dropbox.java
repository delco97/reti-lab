package L04_Monitor.Dropbox;

public class Dropbox {
	private boolean full= false;
	
	private int num;
	
	public synchronized int take(boolean e) {
		
		String s= e ? "Pari" : "Dispari";
		
		boolean pari = (num %2==0) ;
		boolean notok=false;
		if (pari !=e)
		{notok=true;}
		// Nota: la condizione poteva essere codificata in modo molto piu' semplice
		// pero' in questo modo si evidenzia meglio come la condizione debba essere rivalutata
		// ogni volta che il thread viene risvegliato dalla wait
		while (!full || notok) {
			try {
				this.wait();
				notok=false;
				pari= (num %2==0);
				if (pari!=e)
				{notok=true;}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("wait for: " + s);
		}
		System.out.println(s + " took " + num);
		full= false;
		this.notifyAll();
		return num;
	}
	
	public synchronized void put(int n) {
		while (full) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Producer put in " + n);
		num= n;
		full= true;
		this.notifyAll();
	}
}
