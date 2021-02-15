package L02_Threadpools.Es3;

import L02_Threadpools.Es1_Es2.Station;
import L02_Threadpools.Es1_Es2.Traveler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 Scrivere un programma che calcola le potenze di un numero n (esempio n=2) da n2 a n50 e restituisce come risultato
 la somma delle potenze, ovvero:
 
 Result = n2 + n3 + … + n50
 
 Creare una classe Power di tipo Callable che riceve come parametri di ingresso il numero n  e un intero (l’esponente),
 stampa “Esecuzione {n}^{esponente} in {idthread}”  e restituisce il risultato dell’elevamento a potenza (usare la
 funzione Math.pow() di Java https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html#pow-double-double-)
 Creare una classe che nel metodo public static void main(String args[]) crea un threadpool e gli passa i task Power.
 I risultati restituiti dai task vengono recuperati e sommati e il risultato della somma viene stampato (usare una
 struttura dati, es. ArrayList per memorizzare gli oggetti di tipo Future restituiti dal threadpool in
 corrispondenza dell’invocazione del metodo submit).
 */
public class MainClass {
	
	public static void main(String[] args) throws InterruptedException {
		final ExecutorService threadPool = Executors.newCachedThreadPool();
		final int n = 2;
		final int startExp = 2;
		final int finalExp = 5;
		final int timeout = 1000;
		
		double finalRes = 0;
		
		List<Future<Double>> callbacksRes = new ArrayList<>();
		for(int e = startExp; e <= finalExp; e++) {
			Future<Double> res = threadPool.submit(new Power(n, e));
			callbacksRes.add(res);
		}
		System.out.println("Threadpool sta frullando. Aspetto risultati intermedi.");
		//Recupero tutti i risultati e calcolo risultato finale
		for (Future<Double> r : callbacksRes) {
			try {
				finalRes += r.get(timeout, TimeUnit.MILLISECONDS);
			} catch (ExecutionException e) {
				e.printStackTrace();
				System.out.println("Si è verificato un errore durante il calcolo del risultato.");
				System.exit(1);
			} catch (TimeoutException e) {
				e.printStackTrace();
				System.out.println("Non ho tutto il giorno...");
				System.exit(1);
			}
		}
		System.out.printf("Result = %f\n", finalRes);
		threadPool.shutdown();
	}
	
}
