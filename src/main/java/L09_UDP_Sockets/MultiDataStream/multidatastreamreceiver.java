package L09_UDP_Sockets.MultiDataStream;

import java.io.*;
import java.net.*;

public class multidatastreamreceiver {
	
	public static void main(String args[]) throws Exception {// fase di inizializzazione
		FileOutputStream fw = new FileOutputStream("text.txt");
		DataOutputStream dr = new DataOutputStream(fw);
		int port = 13350;
		DatagramSocket ds = new DatagramSocket(port);
		byte[] buffer = new byte[200];
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
		for (int i = 0; i < 10; i++) {
			//Ricezione intero
			ds.receive(dp);
			ByteArrayInputStream bin = new ByteArrayInputStream(dp.getData(), 0, dp.getLength());
			DataInputStream ddis = new DataInputStream(bin);
			int x = ddis.readInt();
			dr.writeInt(x);
			System.out.println(x);
			//Ricezione stringa
			ds.receive(dp);
			bin = new ByteArrayInputStream(dp.getData(), 0, dp.getLength());
			ddis = new DataInputStream(bin);
			String y = ddis.readUTF();
			System.out.println(y);
		}
	}
	
}