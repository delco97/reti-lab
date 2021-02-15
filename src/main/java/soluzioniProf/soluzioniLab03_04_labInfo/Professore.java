package soluzioniProf.soluzioniLab03_04_labInfo;

/**
 * @author Andrea Michienzi
 *
 */
public class Professore extends Utente {

	public Professore(int matricola, Tutor t) {
		super(matricola,t);
	}
	
	@Override
	void request() {
		tutor.professoreRequestLab(this);
		System.out.printf("Professore %s: assegnato tutto il laboratorio \n", this.getMatricola());	
	}
	
	@Override
	void release() {
		System.out.printf("Professore %s: rilascia il lab \n",this.getMatricola());
		tutor.professoreReleaseLab(this);
	}
	
//	public void run() {
//		int k = 1; //TODO: generare un numero randon di volte  accede al laboratorio
//		for(int i=0; i<k;i++) {
//			try {
//				tutor.professoreRequestLab(this);
//				System.out.printf("professore %s: assegnato tutto il laboratorio \n",name);
//				Thread.sleep(2000);
//				tutor.professoreReleaseLab(this);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
}
