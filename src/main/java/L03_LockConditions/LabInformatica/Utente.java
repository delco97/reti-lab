package L03_LockConditions.LabInformatica;


import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/*
Utente task used to simulate a Utente behaviour
 */
public abstract class Utente implements Runnable {
	private int matricola;
	private int k; //number of access to perform
	final Tutor tutor;
	protected long sleepTime;
	protected long workTime;
	
	public Utente(int matricola, Tutor t) {
		Random rand = new Random();
		this.k = 1; //rand.nextInt(10);    // random number from [0,9]
		this.matricola = matricola;
		this.tutor = t;
		this.sleepTime = (long) (Math.random()*5000);
		this.workTime = (long) (Math.random()*3000);
	}
	
	public int getMatricola() {
		return matricola;
	}
	
	public void run() {
		try {
			Thread.sleep(sleepTime);  	// tempo di arrivo random
			// System.out.printf("Utente %d: accede %d volte.\n",matricola,k);
			for(int i=0; i <k; i++) {
				request();				// richiede al tutor il pc (se tesista), il lab (se prof), qualsiasi pc (se studente)
				Thread.sleep(workTime); // tempo  di lavoro su un computer o laboratorio.
				release();				// rilascia: un pc (se tesista), il lab (se prof), un pc (se studente)
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public abstract void request();
	
	abstract void release();
}
