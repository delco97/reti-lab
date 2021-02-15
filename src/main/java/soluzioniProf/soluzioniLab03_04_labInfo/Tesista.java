package soluzioniProf.soluzioniLab03_04_labInfo;

/**
 * @author Andrea Michienzi
 *
 */
public class Tesista extends Utente {//Thread{
	private Tutor tutor;
	private int matricola;
	private int computerRichiesto;
	
	public Tesista(int matricola, Tutor t, int pcRequested) {
		super(matricola,t);
		this.matricola = matricola;
		this.tutor = t;
		computerRichiesto = pcRequested;
	}
	public int getMatricola() {
		return matricola;
	}
	
	public int getComputerRichiesto() {
		return computerRichiesto;
	}
	
	@Override
	void request() {
		tutor.tesistaRequestComputer(this, computerRichiesto);
		System.out.printf("Tesista %d: assegnato pc %d \n",matricola,computerRichiesto);
		
	}
	@Override
	void release() {
		System.out.printf("Tesista %d: rilascia pc %d \n", this.getMatricola(),computerRichiesto);
		tutor.tesistaReleaseComputer(this, computerRichiesto);
	}
	
	
//	public void run() {
//		int k = 1;
//		for(int i=0; i<k;i++) {
//			try {
//				tutor.tesistaRequestComputer(this, computerRichiesto);
//				System.out.printf("Tesista %d: assegnato pc %d \n",matricola,computerRichiesto);
//				Thread.sleep(2000);
//				tutor.tesistaReleaseComputer(this, computerRichiesto);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
}
