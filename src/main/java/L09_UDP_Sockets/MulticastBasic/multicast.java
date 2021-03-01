package L09_UDP_Sockets.MulticastBasic;

import java.net.*;
import java.io.*;

public class multicast {
	
	public static void main(String[] args) {
		
		try {
			//***IMPORTANTE***
			//System.setProperty("java.net.preferIPv4Stack" , "true"); //Così non funziona!
			//Occorre definire -Djava.net.preferIPv4Stack=true nel comando di run del programma. Qui su intelliJIDEA
			//Fai ctrl + alt e modifica configurazione di run in modo da passare parametro -Djava.net.preferIPv4Stack=true
			// in "VM options"
			MulticastSocket ms = new MulticastSocket(4000);
			
			InetAddress ia = InetAddress.getByName("228.0.0.4");
			NetworkInterface it = ms.getNetworkInterface();
			System.out.println(it);
			//ms.setNetworkInterface(NetworkInterface.getByName("en0"));
			ms.joinGroup(ia);
			
		} catch (IOException ex) {
			System.out.println("errore");
			ex.printStackTrace();
		}
		
	}
	
}