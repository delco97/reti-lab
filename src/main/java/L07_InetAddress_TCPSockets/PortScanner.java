package L07_InetAddress_TCPSockets;

import java.net.*;
import java.io.*;

public class PortScanner {
	
	public static void main(String args[]) throws UnknownHostException {
		String host_str;
		try {
			host_str = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			host_str = "localhost";
		}
		InetAddress host = InetAddress.getByName(host_str);
		for (int i = 1; i < 1024; i++) {//Loop well-known ports
			try {
				//Socket s = new Socket(host_str, i); //inefficiente, viene fatto un DNS lookup ogni volta
				Socket s = new Socket(host, i);
				System.out.println("Esiste un servizio sulla porta" + i);
			} catch (UnknownHostException ex) {
				System.out.println("Host Sconosciuto");
				break;
			} catch (IOException ex) {
				//System.out.println("Non esiste un servizio sulla porta" + i);
			}
		}
	}
	
}
