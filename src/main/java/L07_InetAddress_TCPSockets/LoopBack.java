package L07_InetAddress_TCPSockets;

import java.net.*;
import java.util.*;

public class LoopBack {
	
	public static void main(String[] args) throws Exception {
		try {
			InetAddress local = InetAddress.getByName("127.0.0.1");
			NetworkInterface ni = NetworkInterface.getByInetAddress(local);
			if (ni == null) {
				System.err.println("That's weird. No local loopback address.");
			} else System.out.println(ni);
		} catch (UnknownHostException ex) {
			System.err.println("That's weird. No local loopback address.");
		}
	}
	
}
