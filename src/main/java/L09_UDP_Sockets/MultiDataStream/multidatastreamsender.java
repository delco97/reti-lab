package L09_UDP_Sockets.MultiDataStream;

import java.io.*;
import java.net.*;

public class multidatastreamsender {
	
	public static void main(String args[]) throws Exception {// fase di inizializzazione
		InetAddress ia = InetAddress.getByName("localhost");
		int port = 13350;
		DatagramSocket ds = new DatagramSocket();
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		byte[] data = new byte[20];
		DatagramPacket dp = new DatagramPacket(data, data.length, ia, port);
		for (int i = 0; i < 10; i++) {
			//Invio Int
			dout.writeInt(i); //Scrittura di dati strutturati (non singoli byte)
			data = bout.toByteArray(); //Ottengo array di byte associato a dati inseriti
			dp.setData(data, 0, data.length); //Definisco campo data del pacchetto da inviare
			dp.setLength(data.length); //Definisco byte del buffer da inviare
			ds.send(dp);//Invio pacchetto
			bout.reset();//Resetto per prossimo invio
			//Invio Stringa
			dout.writeUTF("***");
			data = bout.toByteArray();
			dp.setData(data, 0, data.length);
			dp.setLength(data.length);
			ds.send(dp);
			bout.reset();
		}
	}
	
}