package L07_InetAddress_TCPSockets;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressExample {
	
	public static void main(String[] args) throws UnknownHostException {
		// print the IP Address of your machine (inside your local network)
		System.out.println(InetAddress.getLocalHost().getHostAddress());
		// print the IP Address of a web site
		System.out.println(InetAddress.getByName("www.java.com"));
		// print all the IP Addresses that are assigned to a certain domain
		InetAddress[] inetAddresses = InetAddress.getAllByName("www.amazon.com");
		for (InetAddress ipAddress : inetAddresses) {
			System.out.println(ipAddress);
		}
	}
	
}
