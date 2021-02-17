package soluzioniProf.soluzioniLab06_Bank;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Creare un file contenente oggetti che rappresentano i conti correnti di una banca. Ogni conto corrente contiene
 * il nome del correntista ed una lista di movimenti. I movimenti registrati per un conto corrente sono relativi
 * agli ultimi 2 anni, quindi possono essere molto numerosi.
 * - per ogni movimento vengono registrati la data e la causale del movimento.
 * - L'insieme delle causali possibili  fissato: Bonifico, Accredito, Bollettino, F24, PagoBancomat.
 * - NB: Scrivete un programma che crei il file
 * Scrivere un programma che rilegge il file e trova, per ogni possibile causale, quanti movimenti hanno quella causale.
 * - progettare un'applicazione che attiva un insieme di thread. Uno di essi legge dal file gli oggetti conto corrente e
 *   li passa, uno per volta, ai thread presenti in un thread pool.
 * - ogni thread calcola il numero di occorrenze di ogni possibile causale all'interno di quel conto corrente ed aggiorna un contatore globale.
 * - alla fine il programma stampa per ogni possibile causale il numero totale di occorrenze.
 * Utilizzare NIO per l'interazione con il file e JSON per la serializzazione
 *
 * @author Andrea Michienzi
 * @version 1.0
 */
public class MainClass {

	/**
	 * numero di conti corrente da inserire
	 */
	private static final int N_CONTI_CORRENTE = 5;
	/**
	 * numero di movimenti da inserire
	 */
	private static final int N_MOVIMENTI = 10;
	/**
	 * nome del file json in cui inserire i conti corrente
	 */
	private static final String FILENAME = "src/main/java/soluzioniProf/soluzioniLab06_Bank/data-conticorrenti.json";


	public static void main(String[] args) throws IOException {
		Random rnd = new Random();
		Causale[] causali = Causale.values();

		//cancella il file se esiste già, per crearlo ex novo
		File file = new File(FILENAME);
		if (file.exists())
			file.delete();
		file.createNewFile();
		FileChannel outChannel = FileChannel.open(Paths.get(FILENAME), StandardOpenOption.APPEND);
		
		int count = 0;
		for (int i = 1; i <= N_CONTI_CORRENTE; i++) {
			ContoCorrente cc = new ContoCorrente("Cliente_" + i);
			for (int j = 0; j < N_MOVIMENTI; j++) {
				Movimento movimento = new Movimento(
						new Date(new Random().nextLong()),
						causali[rnd.nextInt(causali.length)]
				);
				cc.addMovimento(movimento);
				count++;
			}
			scritturaContoCorrente(cc, outChannel); //hey! =) *wink wink* ctrl+click
		}
		outChannel.close();
		System.out.printf("Totale conto correnti creati: %d\n", N_CONTI_CORRENTE);
		System.out.printf("Totale movimenti creati: %d\n", count);
		

		/*
		 * instanzia e avvio il thread che si occupa della lettura del file e del conto
		 * delle occorrenze delle varie causali
		 */
		Lettore reader = new Lettore(new File(FILENAME));
		
		reader.start();
		try {
			reader.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		reader.printResults();
		
	}

	/**
	 * scrive i conti corrente contenuti in listaConti nel file json
	 *
	 * @throws IOException se si verifica un errore di I/O
	 */
	public static void scritturaContoCorrente(ContoCorrente contocorrente, FileChannel outChannel) throws IOException {

		// Serializzazione con Jackson
		ObjectMapper mapper = new ObjectMapper();
		// abilita la scrittura con indentazione per rendere il file .json pi leggibile
//		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		/*
		 * rende visibile all'ObjectMapper gli attributi privati della classe di cui l'oggetto da serializzare
		 * ne l'istanza
		 */
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		// configura la formattazione della data
		mapper.setDateFormat(new SimpleDateFormat("dd-MMM-yy"));
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		byte[] content = (mapper.writeValueAsString(contocorrente)+"\n").getBytes(); //uppure un carattere speciale, tipo | (barra verticale)

		// APPEND serve per APPENDere (scrivere in fondo, senza sovrascrivere
		// Se non ci piace aprire il file in append, basta aprirlo una volta nel main e passare il canale come argomento alla funzione (o una qualsiasi delle infinitomila varianti)
		ByteBuffer bb = ByteBuffer.wrap(content);
		while (bb.hasRemaining()) outChannel.write(bb);

	}

}

