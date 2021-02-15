package L01_IntroduzioneThread.Assignment01_PI;


import java.util.InputMismatchException;
import java.util.Scanner;

class RCalculatePI implements Runnable {
	private double accuracy;
	private double pi;
	
	public double getPi() {
		return pi;
	}
	
	public double getAccuracy() {
		return accuracy;
	}
	
	public RCalculatePI(double p_accuracy) {
		accuracy = p_accuracy;
		pi = 0;
	}
	
	@Override
	public void run() {
		double i = 1;
		pi = 0;
		boolean minus = false;
		do {
			pi +=  (minus?-1:1) * 4/i;
			minus = !minus;
			i+=2;
		}while (!Thread.currentThread().isInterrupted() && Math.abs(Math.PI - pi) >= accuracy);
	}
	
}

public class MainClass {
	public static void main(String[] args) {
		long timeout; 		// time indica il tempo massimo impiegato per il calcolo espresso in ms
		double accuracy; 	//acc indica la soglia di approssimazione
		System.out.println("Inserisci prima tempo e poi accuratezza");
		
		// prende in input time e accuracy
		Scanner input = new Scanner(System.in);
		try {
			timeout = input.nextLong();
			accuracy	= input.nextDouble();
		} catch (InputMismatchException e) { 	// fatal error
			throw e;
		}
		finally {
			input.close();
		}
		//Preparation of the thread to execute
		RCalculatePI piTask = new RCalculatePI(accuracy);
		Thread t = new Thread(piTask);
		t.start();
		try {
			t.join(timeout);
		} catch (InterruptedException e) {
			System.out.println("Thread " + t.getName() + " interrupted!");
		}
		System.out.println("End of program");
		// interrupt thread if still alive
		if (t.isAlive()) {
			t.interrupt();
			System.out.println("Out of time!");
		}
		else
			System.out.printf("Evaluation performed correctly \nThe approximate value of PI is %f\n", piTask.getPi());
	}
}
