package L09_UDP_Sockets;

import java.net.*;

public class IndirizziIP {
	
	public static void main(String args[]) throws Exception {
		String default_host = "localhost";
		String host = args.length > 0 ? args[0]:default_host;
		InetAddress address = InetAddress.getByName(args[0]);
		
		if (address.isMulticastAddress()) {
			
			if (address.isMCGlobal()) {
				System.out.println("e' un indirizzo di multicast globale");
			} else if (address.isMCOrgLocal()) {
				System.out.println("e' un indirizzo organization local");
			} else if (address.isMCSiteLocal()) {
				System.out.println("e' un indirizzo site local");
			} else if (address.isMCLinkLocal()) {
				System.out.println("e' un indirizzo link local");
			}
		} else System.out.println("non e' un indirizzo di Multicast");
	}
}