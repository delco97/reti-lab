package L03_LockConditions.LabInformatica;

import java.util.ArrayList;

/**
 * @author Andrea Michienzi
 *
 */
public class Laboratorio {

	private ArrayList<Boolean> computers; // true: a computer is available, false: a computer is occupied
	private int labDim;                   // number of computers in the laboratorio
	
	public Laboratorio(int ldim) {
		labDim = ldim;
		this.computers =  new ArrayList<Boolean>(ldim); 
		for(int i=0; i< labDim; i++)
			computers.add(new Boolean(true));
	}
	
	public boolean isAvalaibleComputer(int i) {
		return computers.get(i);
	}
	
	public int getSize() {
		return labDim;
	}
	
	public void occupyAll() {
		for(int i=0; i< labDim; i++)
			computers.set(i, new Boolean(false));
	}
	
	public void releaseAll() {
		for(int i=0; i< labDim; i++)
			computers.set(i, new Boolean(true));
	}
	
	public void occupyComputer(int id) {
		computers.set(id, new Boolean(false));
	}
	
	public void releaseComputer(int id) {
		computers.set(id, new Boolean(true));
	}
	
	
	public boolean areAllComputerOccupied() {
		if(this.getAvalaibleComputer() == -1) 
			return true;
		else
			return false;
	}
	
	public boolean isEmpty() {
		for (Boolean computer: computers)
			if(computer == false)	
				return false;
		return true;
	}
	
	/**
	 * Get the index of the first available computer.
	 * 
	 * @author Davide Neri
	 * @return the index [0, labDim-1] of the first available computer, -1 if there are no available computers
	 */
	public int getAvalaibleComputer() {
		for(int i=0; i<this.labDim; i++) {
			boolean pcStatus = computers.get(i);
			if(pcStatus)
				return i;
		}
        return -1;
	}
	

	public void printStatusLab() {
		/* 
		 * [] [] [] [x] [x]
		 */
		String p = "\n";
		for(int i=0; i< labDim; i++) {
			if(computers.get(i)) p += i+":[X] ";
			else p +=" [] ";
		}
		System.out.println(p);
	}
	
}
