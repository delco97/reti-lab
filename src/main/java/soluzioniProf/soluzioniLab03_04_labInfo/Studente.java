package soluzioniProf.soluzioniLab03_04_labInfo;

/**
 *  @author Andrea Michienzi
 *
 */
public class Studente extends Utente{ //  Thread{
	private int assignedPcID; // computer assegnato allo studente
	
	public Studente(int matricola, Tutor t) {
		super(matricola,t);
		assignedPcID = -1;
	}
	
	@Override
	void request() {
		this.assignedPcID = tutor.studenteRequestAComputer(this);
		System.out.printf("Studente %d: assegnato pc %d \n",this.getMatricola(),assignedPcID);
		
	}
	
	@Override
	void release() {
		System.out.printf("Studente %d: rilasciato pc %d \n", this.getMatricola(),assignedPcID);
		tutor.studenteReleaseAComputer(this, assignedPcID);
	}
}


//	public void run() {
//		int k = 1; //TODO: generare un numero randon di volte che lo studente accede al laboratorio
//		for(int i=0; i<k;i++) {
//			try {
//				//System.out.printf("Studente "+ matricola + ": richiesto un computer\n");
//				int pcID = tutor.studenteRequestAComputer(this);
//				System.out.printf("Studente %d: assegnato pc %d \n",matricola,pcID);
//				Thread.sleep(5000);
//				tutor.studenteReleaseAComputer(this, pcID);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
