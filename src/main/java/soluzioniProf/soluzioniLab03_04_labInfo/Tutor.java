package soluzioniProf.soluzioniLab03_04_labInfo;

/**
 * @author Andrea Michienzi
 *
 */
public class Tutor {
	
   public Laboratorio laboratorio; 			
//   private ReentrantLock lockLaboratorio;    
//   private Condition studenteAwaiting;     // studenti in attesa di accedere ad un qualsiasi computer
//   private Condition professoreAwaiting;   // professore in attesa di accedere al laboratorio
//   private Condition[] tesitiAwaiting;     // lista dei tesisti in attesa su ogni singolo computer

   //   private int studenteAwaiting;       // -- non serve perch� si utilizza notifyAll
   private int professoreAwaiting;
   private int[] tesistiAwaiting;

   public Tutor(int lsize) {
		this.laboratorio = new Laboratorio(lsize); 
//		this.lockLaboratorio = new ReentrantLock();
//		this.studenteAwaiting  = lockLaboratorio.newCondition();
//		this.professoreAwaiting = lockLaboratorio.newCondition();
		this.tesistiAwaiting = new int[laboratorio.getSize()];
		for(int i=0; i<laboratorio.getSize(); i++)
			tesistiAwaiting[i] = 0;
   }	
   
   public int getAvailableComputer() {
		return laboratorio.getAvalaibleComputer();
	}
	
   public synchronized void professoreRequestLab(Professore p) {
//		lockLaboratorio.lock();
//		try {
			try {
				/*
				 * Un professore si mette in attesa se il laboratorio non � vuoto.
				 */
				this.professoreAwaiting ++;
				while(!laboratorio.isEmpty()) {
					System.out.printf("Professore %s: in attesa del lab ...\n", p.getMatricola() );
//					professoreAwaiting.await();
					wait();
				}
				laboratorio.occupyAll();
				this.professoreAwaiting --;
			} catch (InterruptedException e) {e.printStackTrace();}
//		} finally {
//			lockLaboratorio.unlock();
//		}
	}
   
   public synchronized void professoreReleaseLab(Professore p) {
//	   lockLaboratorio.lock();
//		try {
			laboratorio.releaseAll();
			/* 
			 *  Se ci sono altri professori in attesa, vengono risvegliati i professori
			 *  altrimenti vegono risvegliati tutti i tesisti e gli studenti.
			 */
			notifyAll();  
//			if(lockLaboratorio.hasWaiters(professoreAwaiting))
				// sveglia tutti i tasks che sono in wait() sul tutor (professori, studenti, laurenadi)
//			else {
//				for(Condition tesista:tisitiAwaiting)
//					tesista.signal();
//				studenteAwaiting.signalAll();
//			}
//		} finally {
//			lockLaboratorio.unlock();
//		}
   }
	
   public synchronized int studenteRequestAComputer(Studente s){
//		lockLaboratorio.lock();
//		try {
			try {
				/*
				 *  Uno studente si mette in attesa se una delle due condizioni � vera:
				 *     se ci sono professori in attesa, o
				 *     se tutti i pc del laboratorio: (1) sono occupati o (2) ci sono tesisti in attesa
				 */

//			   while(lockLaboratorio.hasWaiters(professoreAwaiting) || getComputerLiberoForStudente() == -1) { 
			   while(this.professoreAwaiting > 0 || getComputerLiberoForStudente() == -1) { 
					System.out.printf("Studente %d: in attesa di un pc libero ...\n", s.getMatricola());
					wait(); //studenteAwaiting.await();
				}
				int pcID = getComputerLiberoForStudente();
				laboratorio.occupyComputer(pcID);
				return pcID;
			} catch (InterruptedException e) {e.printStackTrace();} 
//		}finally {
//		   lockLaboratorio.unlock();
//		}
		return -1;
	}
	
   public synchronized void studenteReleaseAComputer(Studente s, int pcID) {
//		lockLaboratorio.lock();
//		try {
			laboratorio.releaseComputer(pcID);
			/*
			 *  Se ci sono professori in attesa, risvegli ai professori,
			 *  altrimenti se ci sono tesisti in attesa sullo stesso computer li sveglia,
			 *  altrimenti sveglia uno studente.
			 */
			notifyAll();
//			if(lockLaboratorio.hasWaiters(professoreAwaiting))
//				professoreAwaiting.signal();
//			else if(lockLaboratorio.hasWaiters(tisitiAwaiting[pcID]))
//				tisitiAwaiting[pcID].signal();
//			else
//				studenteAwaiting.signal();
//		} finally {
//			lockLaboratorio.unlock();
//		}

	}

   public synchronized void tesistaRequestComputer(Tesista t, int pcID) {
//		lockLaboratorio.lock();
//		try {
			try {
				/*
				 * Il tesista si mette in attesa se:
				 * 		 se ci sono professori in attesa di prendere il laboratorio, o 
				 *       se il computer richiesto � occupato	
				 */
				this.tesistiAwaiting[pcID] ++;
//				while(lockLaboratorio.hasWaiters(professoreAwaiting) ||  !laboratorio.isAvalaibleComputer(pcID)) {
				while(this.professoreAwaiting > 0  ||  !laboratorio.isAvalaibleComputer(pcID)) {
					System.out.printf("Tesista %d: in attesa sul pc %d ... \n", t.getMatricola(),pcID);
					wait();
				}
				this.tesistiAwaiting[pcID]--;
				laboratorio.occupyComputer(pcID);
			} catch (InterruptedException e) {e.printStackTrace();}
//		} finally {
//			lockLaboratorio.unlock();
//		}
	}
	
   public synchronized void tesistaReleaseComputer(Tesista t, int pcID) {
//		lockLaboratorio.lock();
//		try {
			laboratorio.releaseComputer(pcID);
			notifyAll();
			
			/*
			 * 	Se ci sono professori in attesa, sveglia i professori
			 *  altrimenti se ci sono tesisti in attesa dello stesso computer li sveglia,
			 *  altrimenti sveglia uno studente.
			 */
//			if(lockLaboratorio.hasWaiters(professoreAwaiting))
//				professoreAwaiting.signal();
//			else if(lockLaboratorio.hasWaiters(tisitiAwaiting[pcID]))
//				tisitiAwaiting[pcID].signal();
//			else
//				studenteAwaiting.signal();
//		} finally {
//			lockLaboratorio.unlock();
//		}
	}

   public synchronized int getComputerLiberoForStudente() {
	   // ritorna l'indice di un computer libero da assegnare a uno studente
	   // in cui non ci sono tesisti in attesa.
	   for (int i =0; i<laboratorio.getSize(); i++) {
//		   if(laboratorio.isAvalaibleComputer(i) && lockLaboratorio.hasWaiters(tisitiAwaiting[i]) == false)
			if(laboratorio.isAvalaibleComputer(i) && this.tesistiAwaiting[i] == 0)
			   return i;
	   }
	   return -1;
   }
}
