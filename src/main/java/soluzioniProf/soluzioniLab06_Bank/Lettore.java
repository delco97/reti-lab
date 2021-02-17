package soluzioniProf.soluzioniLab06_Bank;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Lettore modella il lettore che legge del file json e passa i conti corrente (uno per volta) ai "contatori".
 * Il lettore tiene un riferimento al contatore globale delle occorrenze
 *
 * @author Andrea Michienzi
 * @version 1.0
 */
public class Lettore extends Thread {
	/**
	 * dimensione del buffer
	 */
	private static final int BUFF_SIZE = 2048; //(int) Math.pow(2, 30); yes but no =)

	/**
	 * file da cui leggere i conti corrente
	 */
	private final File file;

	/**
	 * threadpool per contare le occorrenze delle causali
	 */
	private final ExecutorService pool;

	/**
	 * contatore globale delle occorrenze (condiviso dai thread)
	 */
	private final ConcurrentHashMap<Causale, Long> globalCounter;


	/**
	 *
	 * @param input file da cui leggere
	 */
	public Lettore(File input) {
		this.file = input;
		pool = Executors.newCachedThreadPool();
 		globalCounter = new ConcurrentHashMap<Causale, Long>();
		Causale[] causali = Causale.values();

		for (Causale c: causali) {
			globalCounter.put(c, 0L);
		}
	}

	@Override
	public void run() {
		System.out.println("Lettore: inzio lettura dei contocorrenti");
		ByteBuffer buffer = ByteBuffer.allocate(BUFF_SIZE);
		List<Byte> jsonobject=new LinkedList<Byte>();
		
		try ( FileChannel inChannel= FileChannel.open(Paths.get(this.file.getPath()), StandardOpenOption.READ) ) {
			//leggi
			while (inChannel.read(buffer) != -1) {
				buffer.flip();
				//cerca \n
				while(buffer.hasRemaining()) {
					byte b=buffer.get();
					if(b==10) { //oggetto finito! passare ad un contatore!
						pool.execute(new Contatore(jsonobject, globalCounter));
						jsonobject=new LinkedList<Byte>();
					} else { //aggiungi byte alla lista
						jsonobject.add(b);
					}
				}
				buffer.clear();
			}
			while (inChannel.read(buffer) != -1) {
				buffer.flip();
				while(buffer.hasRemaining()) {
					byte b=buffer.get();
					jsonobject.add(b);
				}
				pool.execute(new Contatore(jsonobject, globalCounter));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}




		// chiusura "gentile" del threadpool
		this.pool.shutdown();
		try {
			while(!this.pool.isTerminated())
				this.pool.awaitTermination(60, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * stampa i risultati del conteggio delle occorrenze
	 */
	public void printResults() {
		System.out.println("-------------");
		int total =0;
		for (Causale c: globalCounter.keySet()) {
			total += globalCounter.get(c);
			System.out.printf("Causale: %s, # occorrenze: %d \n", c.toString(), globalCounter.get(c));
		}
		System.out.println("-------------");
		System.out.println("Totale: " + total);
		System.out.println("-------------");
	}
}
