package L05_IO_Serialization.FileCrawler;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class FoldersQueue {
	List<File> targetFolders;
	/**
	 * variabile booleana con la seguente semantica
	 * 		true se non ci sono più item da aggiungere
	 * 		false altrimenti
	 */
	private boolean done;
	
	public FoldersQueue() {
		this.targetFolders = new LinkedList<>();
		done = false;
	}
	
	public synchronized void push(File f) {
		if(!f.isDirectory()) throw new IllegalArgumentException("You must pass a folder!");
		targetFolders.add(f);
		notifyAll();
	}
	
	public synchronized File pop() {
		while (targetFolders.isEmpty() && !done) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(targetFolders.size() > 0) {
			notifyAll();
			return targetFolders.remove(targetFolders.size() - 1);
		} else return null;
	}
	
	public synchronized void finishSearch() {
		done = true;
		notifyAll();
	}
}
