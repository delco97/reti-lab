package soluzioniProf.soluzioniLab06_Bank;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Contatore modella un task che si occupa di contare il numero di movimenti per causale
 *
 * @author Andrea Michienzi
 * @version 1.0
 */
public class Contatore implements Runnable {
	private List<Byte> bytelist;
	/**
	 * contatore globale delle occorrenze
	 */
	private final ConcurrentHashMap<Causale, Long> globalCounter;
	/**
	 * contatore locale delle occorrenze
	 */
	private final HashMap<Causale, Integer> localCounter;

	/**
	 *
	 */
	public Contatore(List<Byte> bl, ConcurrentHashMap<Causale, Long> counters) {
		super();
		bytelist=bl;
		this.globalCounter = counters;
		localCounter = new HashMap<Causale, Integer>();
		for (Causale c: Causale.values()){
			localCounter.put(c, 0);
		}
	}


	@Override
	public void run() {
		
		byte[] jsonbytes=new byte[bytelist.size()];
		int j=0;
		for(Byte by: bytelist) jsonbytes[j++] = by.byteValue();
		
		ObjectMapper mapper = new ObjectMapper();
		/*
		 * rende visibile all'ObjectMapper gli attributi privati della classe di cui l'oggetto da deserializzare
		 * ne  l'istanza
		 */
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		// configura la formattazione della data
		mapper.setDateFormat(new SimpleDateFormat("dd-MMM-yy"));

		ContoCorrente cc=null;
		try {
			 cc = mapper.reader()
					.forType(new TypeReference<ContoCorrente>() {})
					.readValue(jsonbytes);	 // lettura dal buffer
		}
		catch (IOException e){
			e.printStackTrace();
			return;
		}
		
		int n = cc.getN_movimenti();

		// calcola le occorrenze locali delle causali
		for(int i=0; i<n; i++){
			Movimento m = cc.getMovimento(i);
			Causale c = m.getCausale();
			localCounter.put(c, localCounter.get(c)+1);
		}

		// comunica le occorrenze ottenute localmente al contatore globale
		/*
		for (Map.Entry<Causale, Integer> e: localCounter.entrySet()) {
			globalCounter.computeIfPresent(e.getKey(), (c, k) -> k + e.getValue());
		}
		*/
//		oppure
		for (Causale k : globalCounter.keySet()) {
			synchronized(k) {
				globalCounter.put(k, globalCounter.get(k)+localCounter.get(k));
			}
		}
//		oppure una delle altre infinitomila maniere!
	}
}
