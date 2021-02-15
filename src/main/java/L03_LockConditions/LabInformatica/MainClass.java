package L03_LockConditions.LabInformatica;

import L02_Threadpools.Es3.Power;

import java.util.*;
import java.util.concurrent.*;

/**
 * Il laboratorio di Informatica del Polo Marzotto è utilizzato da tre tipi di utenti, studenti, tesisti e professori ed ogni utente deve fare una richiesta al tutor per accedere al laboratorio. I computers del laboratorio sono numerati da 1 a 20. Le richieste di accesso sono diverse a seconda del tipo dell'utente:
 * <p>
 * i professori accedono in modo esclusivo a tutto il laboratorio, poichè hanno necessità di utilizzare tutti i computers per effettuare prove in rete.
 * i tesisti richiedono l'uso esclusivo di un solo computer, identificato dall'indice i, poichè su quel computer è istallato un particolare software necessario per lo sviluppo della tesi.
 * gli studenti richiedono l'uso esclusivo di un qualsiasi computer.
 * I professori hanno priorità su tutti nell'accesso al laboratorio, i tesisti hanno priorità sugli studenti.
 * <p>
 * Nessuno però può essere interrotto mentre sta usando un computer . Scrivere un programma JAVA che simuli il comportamento degli utenti e del tutor. Il programma riceve in ingresso il numero di studenti, tesisti e professori che utilizzano il laboratorio ed attiva un thread per ogni utente. Ogni utente accede k volte al laboratorio, con k generato casualmente. Simulare l'intervallo di tempo che intercorre tra un accesso ed il successivo e l'intervallo di permanenza in laboratorio mediante il metodo sleep. Il tutor deve coordinare gli accessi al laboratorio. Il programma deve terminare quando tutti gli utenti hanno completato i loro accessi al laboratorio.
 */

public class MainClass {
	final static int NUM_COMPUTERS = 2; // number of pc in the lab
	
	public static void main(String[] args) throws InterruptedException {
		
//		if(args.length != 3) {
//			System.err.println("Usage: \n GestioneMainLaboratorio numStudenti numTesisti numProfessori \n"
//					+ "\t numstudenti  \t numero di studenti che accedono al laboratorio \n "
//					+ "\t numTesisti \t numero di tisti che accedono al laboratorio\n"
//					+ "\t numProfessori \tnumero di professori che accedono al laboratorio\n"
//					+ "\n\n Example: GestioneMainLaboratorio 10 5 2.");
//			System.exit(1);
//		}
		
		System.out.printf("Numero di computer %d \n \n",NUM_COMPUTERS);
		
		int ns = 0;  // numero di studenti
		int nt = 0;  // numero di tesisti
		int np = 0;  // numero di professori
		
//		ns=Integer.parseInt(args[0]);
//		nt=Integer.parseInt(args[1]);
//		np=Integer.parseInt(args[2]);
		
		// prende input
		Scanner input = new Scanner(System.in);
		try {
			System.out.println("Inserisci numero studenti: ");
			ns = input.nextInt();
			System.out.println("Inserisci numero tutor: ");
			nt	= input.nextInt();
			System.out.println("Inserisci numero prof: ");
			np	= input.nextInt();
		} catch (InputMismatchException e) { 	// fatal error
			throw e;
		}
		finally {
			input.close();
		}
		
		// creo il tutor
		Tutor t = new Tutor(NUM_COMPUTERS);
		
		// creo e faccio partire gli studenti
		for(int i=0; i<ns;i++) {
			new Thread(new Studente(i, t)).start();
		}
		// creo e faccio partire i tesisti
		for(int i=0; i<nt;i++) {
			Random rand = new Random();
			int randPc = rand.nextInt(NUM_COMPUTERS); // pc che il tesista vuole accedere [0, DIMLAB-1)
			new Thread(new Tesista(i, t, randPc)).start();
		}
		// creo e faccio partire i professori
		for(int i=0; i<np;i++) {
			new Thread(new Professore(i, t)).start();
		}
	}

	
}
